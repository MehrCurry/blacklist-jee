/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
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
