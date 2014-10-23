package prototype.blacklist.boundary;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import static prototype.blacklist.boundary.BlacklistEntryResource.BLACKLIST;

import prototype.blacklist.entity.BlacklistEntry;
import prototype.blacklist.normalize.BlacklistValueNormalizer;

/**
 * Simple service to manage in-mem blacklists via REST.
 *
 * Request/Response handling refers to:
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html.
 */
@Stateless
@Path("blacklist")
public class BlacklistService {

    private static final String ENTRY_URI_TEMPLATE = "entry";
    private static final String ENTRIES_URI_TEMPLATE = "entries";

    /**
     * The URI of this service. (e.g.
     * http://localhost:8080/blacklist-jee7/resources)
     */
    @Context
    private UriInfo uri;

    @Inject
    private Validator validator;

    @Inject
    private BlacklistValueNormalizer normalizer;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Writes the given BlacklistEntry to database. If an entry with the same
     * Value and Type is already in the database, the new Entry won´t be
     * persisted.
     *
     * @param newEntry
     * @return
     */
    public BlacklistEntry add(BlacklistEntry newEntry) {
        normalizer.normalize(newEntry);
        TypedQuery<BlacklistEntry> query = entityManager.createNamedQuery(
                "BlacklistEntry.findByNameAndValue", BlacklistEntry.class);
        query.setParameter("name", newEntry.getName());
        query.setParameter("value", newEntry.getValue());
        List<BlacklistEntry> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            entityManager.persist(newEntry);
            return newEntry;
        } else {
            return resultList.get(0);
        }
    }

    public BlacklistEntry getEntry(long id) {
        return this.entityManager.find(BlacklistEntry.class, id);
    }

    public List<BlacklistEntry> getEntries() {
        List<BlacklistEntry> entries = this.entityManager.createNamedQuery(
                "BlacklistEntry.findAll", BlacklistEntry.class).getResultList();
        return entries;
    }

    /**
     * Returns a collection of URIs to the stores blacklists and thus works as
     * an entry point of the service.
     */
    @GET
    public Response getBlacklistOverview() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        final List<BlacklistEntry> results = entityManager.createNamedQuery("BlacklistEntry.findAll").getResultList();
        results.forEach(entry -> {
            URI entryUri = uri.getBaseUriBuilder().path(BLACKLIST).path(ENTRY_URI_TEMPLATE).path(entry.getId().toString()).build();
            arrayBuilder.add(entryUri.toString());
        });
        return Response.ok().entity(arrayBuilder.build()).build();
    }

    /**
     * Get a specific blacklist by name.
     *
     * @param blacklistName The name of the blacklist
     * @return The blacklist connected to the name or null.
     */
    @GET
    @Path("{blacklistName}")
    public Blacklist getBlacklist(
            @PathParam("blacklistName") String blacklistName) {
        throw new UnsupportedOperationException("operation must be converted to use real entity");
    }

    @POST
    public void addEntry(BlacklistEntry blacklistEntry) {
        normalizer.normalize(blacklistEntry);
        entityManager.persist(blacklistEntry);
    }

    @POST
    @Path("blacklistEntries/{name}/{value}")
    public void addBlacklistEntry(@PathParam("name") String name,
            @PathParam("value") String value) {
        BlacklistEntry blacklistEntry = new BlacklistEntry(name, value);
        normalizer.normalize(blacklistEntry);
        entityManager.persist(blacklistEntry);
    }

    @GET
    @Path("blacklistEntries")
    public List<BlacklistEntry> getBlacklistEntries() {
        TypedQuery<BlacklistEntry> query = entityManager.createNamedQuery(
                "BlacklistEntry.findAll", BlacklistEntry.class);
        List<BlacklistEntry> results = query.getResultList();
        return results;
    }

    /**
     * Puts one or more blacklist entries into a specific blacklist.
     *
     * If the blacklist does not exist a not found response will be sent. If on
     * of the blacklistEntries is malformed a bad request response is given.
     *
     * If a single entry is given a created response will be returned,
     * containing the URI to the new entry. If a list of entries is given a
     * simple ok response will be sent.
     *
     * @param blacklistName The name of the blacklist
     * @param blacklistEntries One or more blacklist entries.
     *
     * @return A response containing informations about the success of the
     * operation.
     */
    @POST
    @Path("{blacklistName}")
    public Response addToBlacklist(
            @PathParam("blacklistName") String blacklistName,
            String... blacklistEntries) {

        throw new UnsupportedOperationException("operation must be converted to use real entity");
    }

    /**
     * Returns a not modified, no content or okay response depending on the
     * request.
     *
     * If the given blacklist name is unknown a not modified response is sent,
     * containing the information that the blacklist is unknown in its
     * not.modified.reason header.
     *
     * If the requested blacklist entry does not exist the no content response
     * is sent. Else a simple ok response will be returned.
     *
     * @param blacklistEntry The identifier of the blacklist entry
     * @param blacklistName Tha name of the blacklist
     *
     * @return A HTTP response.
     */
    @GET
    @Path("{blacklistName}/{blacklistEntry}")
    public Response getBlacklistEntry(
            @PathParam("blacklistEntry") String blacklistEntry,
            @PathParam("blacklistName") String blacklistName) {
        throw new UnsupportedOperationException("operation must be converted to use real entity");
    }

    /**
     * Deletes a requested blacklist entry from a blacklist if both exist.
     *
     * If the given blacklist or the blacklist entry does not exist a not found
     * response is sent. Else, the entry will be removed from the list and a no
     * content response is sent. (see
     * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)
     *
     * @param blacklistEntry The identifier of the blacklist entry
     * @param blacklistName The name of the blacklist
     *
     * @return A HTTP response.
     */
    @DELETE
    @Path("{blacklistName}/{blacklistEntry}")
    public Response deleteFromBlacklist(
            @PathParam("blacklistEntry") String blacklistEntry,
            @PathParam("blacklistName") String blacklistName) {
        throw new UnsupportedOperationException("operation must be converted to use real entity");
    }

    /**
     * Creates the given blacklist and sends a created response if all given
     * blacklist entries match the blacklist entry format. Else a bad request
     * header is returned containing informations about malformed entries in its
     * header.
     *
     * @param blacklist A blacklist given by the request. (e.g. in the body as
     * JSON which depends on the client)
     * @return A HTTP response.
     */
    @PUT
    public Response createBlacklist(Blacklist blacklist) {
        throw new UnsupportedOperationException("operation must be converted to use real entity");

    }

    /**
     * Deletes a requested blacklist if it exist.
     *
     * If the given blacklist does not exist a not found response is sent. Else,
     * the blacklist will be removed and a no content response is sent (see
     * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)
     *
     * @param blacklistName The name of the blacklist
     *
     * @return A HTTP response.
     */
    @DELETE
    @Path("{blacklistName}")
    public Response deleteBlacklist(
            @PathParam("blacklistName") String blacklistName) {
        throw new UnsupportedOperationException("operation must be converted to use real entity");
    }
}
