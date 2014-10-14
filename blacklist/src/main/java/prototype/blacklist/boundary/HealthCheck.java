package prototype.blacklist.boundary;

import java.time.LocalDateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Guido.Zockoll
 */
@Path("health")
public class HealthCheck {
    
    @GET
    @Produces("text/plain")
    public String check() {
        return "I am alive " + LocalDateTime.now();
    }
}
