package prototype.blacklist.boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import prototype.blacklist.entity.BlacklistEntry;

@Stateless
@Path(BlacklistEntryResource.BLACKLIST)
public class BlacklistEntryResource {
	
	static final String BLACKLIST = "blacklist";

	/** The URI of this service. (e.g. http://localhost:8080/blacklist-jee7/resources) */
	@Context
	private UriInfo uri;
                
	@Inject
    private Validator validator;
	
	@Inject
	private BlacklistService blacklistService;
	
	private static final String ENTRY_URI_TEMPLATE = "entry";
	private static final String ENTRIES_URI_TEMPLATE = "entries";
	
	@POST
	@Path(ENTRY_URI_TEMPLATE)
	public Response add(BlacklistEntry newEntry) {
		blacklistService.add(newEntry);
		URI entryUri = uri.getAbsolutePathBuilder().path(newEntry.getBlacklistEntryId().toString()).build();
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
		List<BlacklistEntry> entries = blacklistService.getEntries();
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		for (BlacklistEntry entry : entries) {
			URI entryUri = uri.getBaseUriBuilder().path(BLACKLIST).path(ENTRY_URI_TEMPLATE).path(entry.getBlacklistEntryId().toString()).build();
			arrayBuilder.add(entryUri.toString());
		}
		return Response.ok().entity(arrayBuilder.build()).build();
	}

}
