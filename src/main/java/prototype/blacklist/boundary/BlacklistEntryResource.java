package prototype.blacklist.boundary;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Validator validator;

    @Inject
    private BlacklistValueNormalizer normalizer;

    private static final String ENTRY_URI_TEMPLATE = "entry";
    private static final String ENTRIES_URI_TEMPLATE = "entries";

    @POST
    @Path(ENTRY_URI_TEMPLATE)
    public Response add(BlacklistEntry newEntry) {
        Set<ConstraintViolation<BlacklistEntry>> violations = validator.validate(newEntry);
        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).header("X-Validation-Errors",
                    violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage()).collect(Collectors.toList())
            ).build();
        }
        normalizer.normalize(newEntry);
        entityManager.persist(newEntry);
        URI entryUri = uri.getAbsolutePathBuilder().path(newEntry.getId().toString()).build();
        return Response.created(entryUri).build();
    }

    @GET
    @Path(ENTRY_URI_TEMPLATE + "/{id}")
    public BlacklistEntry getEntry(@PathParam("id") long id) {
        return entityManager.find(BlacklistEntry.class, id);
    }

    @GET
    @Path("example")
    public BlacklistEntry example() {
        return new BlacklistEntry("foo", "bar");
    }

    @GET
    @Path(ENTRIES_URI_TEMPLATE)
    public Response getEntries() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        final List<BlacklistEntry> results = entityManager.createNamedQuery("BlacklistEntry.findAll").getResultList();
        results.forEach(entry -> {
            URI entryUri = uri.getBaseUriBuilder().path(BLACKLIST).path(ENTRY_URI_TEMPLATE).path(entry.getId().toString()).build();
            arrayBuilder.add(entryUri.toString());
        });
        return Response.ok().entity(arrayBuilder.build()).build();
    }
}
