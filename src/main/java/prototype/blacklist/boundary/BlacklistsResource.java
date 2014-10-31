/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import prototype.blacklist.entity.Blacklist;

@Stateless
@Path("/blacklists")
public class BlacklistsResource {    

    @PersistenceContext
    private EntityManager em;
  
    public void setEm(EntityManager em) {
        this.em = em;
    }

    
    public void populateDatabase() {
        em.persist(new Blacklist("hurz").addEntry("foo").addEntry("bar"));
    }
}
