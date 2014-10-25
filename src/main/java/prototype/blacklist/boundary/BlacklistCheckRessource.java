package prototype.blacklist.boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import prototype.blacklist.entity.BlacklistEntry;
import prototype.blacklist.normalize.BlacklistCheckValueNormalizer;

import com.sun.messaging.jmq.io.Status;

@Stateless
@Path(BlacklistCheckRessource.BLACKLIST_CHECKS)
public class BlacklistCheckRessource {

	public static final String BLACKLIST_CHECKS = "blacklistchecks";
	
	@Context
    private UriInfo uri;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Validator validator;

    @Inject
    private BlacklistCheckValueNormalizer normalizer;
    
    
    @GET
    @Path("")
    public Response check(BlacklistCheck check) {
        Set<ConstraintViolation<BlacklistCheck>> violations = validator.validate(check);
        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).header("X-Validation-Errors",
                    violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage()).collect(Collectors.toList())
            ).build();
        }

        normalizer.normalize(check);
        
        List<BlacklistEntry> listedEntries = new ArrayList<BlacklistEntry>();
        Map<String, String> parametersToCheck = check.getParametersToCheck();
        for(Entry<String,String> checkEnty : parametersToCheck.entrySet()){
        	final List<BlacklistEntry> blacklistEntries = entityManager.createNamedQuery("BlacklistEntry.findByNameAndValue").setParameter(":name",checkEnty.getKey() ).setParameter(":value",checkEnty.getValue()).getResultList();
        	listedEntries.addAll(blacklistEntries);
        }
        	                 
        if(listedEntries.isEmpty()){
        	return Response.ok().build();	
        }
        
        return Response.status(Status.BAD_REQUEST).entity(listedEntries).build();
    }
}
