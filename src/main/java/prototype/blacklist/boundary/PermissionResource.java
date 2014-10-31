/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import prototype.blacklist.entity.Blacklist;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Stateless
@Path("/permission")
public class PermissionResource {
    
    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @GET
    @Path("{name}/{value}")
    public Response isGranted(@PathParam("name") String name,@PathParam("value") String value) {
        Object o=em.find(Blacklist.class, 1L);
        return Response.ok().build();
        // Query q=em.createNamedQuery("hurz");
        // q.setParameter("name", name);
        /*
        final List results = Collections.EMPTY_LIST;
        if (results.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).header("X-Info", "unknown blacklist: " + name).build();
        }
        Blacklist blacklist = (Blacklist) results.get(0);
   
        if (blacklist.isBlacklisted(value)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        */
    }
}
