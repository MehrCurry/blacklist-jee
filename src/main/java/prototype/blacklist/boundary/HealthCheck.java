package prototype.blacklist.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.LocalDateTime;


@Path("health")
public class HealthCheck {
    @GET
    public String check() {
        return "I am alive " + LocalDateTime.now();
    }
}