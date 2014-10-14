package prototype.blacklist.boundary;

import java.time.LocalDateTime;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author Guido.Zockoll
 */
@Path("health")
public class HealthCheck {
    
    @GET
    public String check() {
        return "I am alive " + LocalDateTime.now();
    }
}
