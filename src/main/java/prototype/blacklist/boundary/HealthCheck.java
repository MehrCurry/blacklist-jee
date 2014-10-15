package prototype.blacklist.boundary;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.LocalDateTime;


@Path("health")
public class HealthCheck {
    @GET
    public JsonObject check() {
        return Json.createObjectBuilder().add("message", "I am alive").add("localtime", LocalDateTime.now().toString()).build();
    }
}