package prototype.blacklist.boundary;

import java.net.URI;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import prototype.blacklist.entity.BlacklistEntry;
import prototype.blacklist.normalize.BlacklistValueNormalizer;

@Stateless
@Path(BlacklistEntryResource.BLACKLIST)
public class BlacklistEntryResource {
	
	static final String BLACKLIST = "blacklist";

	@Context
	private UriInfo uri;
	
	@Inject
        private Validator validator;

        @Inject
        private BlacklistValueNormalizer normalizer;

	@Inject
	private BlacklistService blacklistService;
	
	private static final String ENTRY_URI_TEMPLATE = "entry";
	private static final String ENTRIES_URI_TEMPLATE = "entries";
	
	@POST
	@Path(ENTRY_URI_TEMPLATE)
	public Response add(BlacklistEntry newEntry) {
		Set<ConstraintViolation<BlacklistEntry>> violations = validator.validate(newEntry);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException("Error saving BlacklistEntry: " + newEntry, violations);
		}
                normalizer.normalize(newEntry);
		BlacklistEntry entry = blacklistService.add(newEntry);
		URI entryUri = uri.getAbsolutePathBuilder().path(entry.getBlacklistEntryId().toString()).build();
		return Response.created(entryUri).build();
	}
	
	@GET
	@Path(ENTRY_URI_TEMPLATE+"/{id}")
	public BlacklistEntry getEntry(@PathParam("id") long id) {
		return blacklistService.getEntry(id);
	}
	
	@GET
	@Path(ENTRIES_URI_TEMPLATE)
	public Response getEntries() {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		blacklistService.getEntries().forEach(entry -> {
			URI entryUri = uri.getBaseUriBuilder().path(BLACKLIST).path(ENTRY_URI_TEMPLATE).path(entry.getBlacklistEntryId().toString()).build();
			arrayBuilder.add(entryUri.toString());
		});
		return Response.ok().entity(arrayBuilder.build()).build();
	}
}
