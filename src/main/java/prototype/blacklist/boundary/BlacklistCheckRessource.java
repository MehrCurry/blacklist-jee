package prototype.blacklist.boundary;

import java.util.ArrayList;
import java.util.HashSet;
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
import prototype.blacklist.normalize.BlacklistValueNormalizer;

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
    private BlacklistValueNormalizer normalizer;
    
    
    @GET
    @Path("")
    public Response check(BlacklistCheck check) {
        Set<ConstraintViolation<BlacklistCheck>> violations = validator.validate(check);
        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).header("X-Validation-Errors",
                    violations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage()).collect(Collectors.toList())
            ).build();
        }

        Set<ConstraintViolation<BlacklistEntry>> entryViolations = new HashSet<ConstraintViolation<BlacklistEntry>>();
        for(BlacklistEntry entry : check.getParametersToCheck()){
        	entryViolations.addAll(validator.validate(entry));
        	normalizer.normalize(entry);
        }
        
        if (!entryViolations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).header("X-Validation-Errors",
            		entryViolations.stream().map(v -> v.getPropertyPath() + " " + v.getMessage()).collect(Collectors.toList())
            ).build();
        }
                
        List<BlacklistEntry> listedEntries = new ArrayList<BlacklistEntry>();
        for(BlacklistEntry entry : check.getParametersToCheck()){
        	final List<BlacklistEntry> blacklistEntries = entityManager.createNamedQuery("BlacklistEntry.findByNameAndValue").setParameter(":name",entry.getName() ).setParameter(":value",entry.getValue()).getResultList();
        	listedEntries.addAll(blacklistEntries);
        }
        	                 
        if(listedEntries.isEmpty()){
        	return Response.ok().build();	
        }
        
        return Response.status(Status.BAD_REQUEST).entity(listedEntries).build();
    }
}
