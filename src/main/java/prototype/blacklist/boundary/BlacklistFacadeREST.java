/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import prototype.blacklist.entity.Blacklist;

/**
 *
 * @author Guido.Zockoll
 */
@Stateless
@Path("blacklists")
public class BlacklistFacadeREST extends AbstractFacade<Blacklist> {
    @PersistenceContext(unitName = "prod")
    private EntityManager em;

    public BlacklistFacadeREST() {
        super(Blacklist.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Blacklist entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Blacklist entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Blacklist find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @OPTIONS
    @Produces({"application/xml", "application/json"})
    public Blacklist example() {
        return new Blacklist("example").addEntry("Test").addEntry("DE89 3704 0044 0532 0130 00");
    }
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Blacklist> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Blacklist> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}