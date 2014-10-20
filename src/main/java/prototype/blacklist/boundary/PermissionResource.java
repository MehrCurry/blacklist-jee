/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/permission")
public class PermissionResource {
    
    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @GET
    @Path("{type}/{value}")
    public Response isGranted(@PathParam("type") String type,@PathParam("value") String value) {
        Query q=em.createNamedQuery("BlacklistEntry.findByType");
        q.setParameter("type", type);
        final List results = q.getResultList();
        if (results.contains(value)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }
}
