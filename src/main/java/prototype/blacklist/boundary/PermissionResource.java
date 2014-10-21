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
    @Path("{name}/{value}")
    public Response isGranted(@PathParam("name") String name,@PathParam("value") String value) {
        Query q=em.createNamedQuery("BlacklistEntry.findByNameAndValue");
        q.setParameter("name", name);
        q.setParameter("value",value);
        final List results = q.getResultList();
        if (results.size()>0) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }
}
