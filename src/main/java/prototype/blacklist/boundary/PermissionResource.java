/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import java.util.Collections;
import java.util.List;
import prototype.blacklist.entity.Blacklist;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionResource {
    
    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @RequestMapping(value = "/permission/{name}/{value}", method = RequestMethod.GET)
    public void isGranted(@PathVariable String name,@PathVariable String value, HttpServletResponse response) {
        Query q=em.createNamedQuery("hurz");
        q.setParameter("name", name);

        final List results = Collections.EMPTY_LIST;
        if (results.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setHeader("X-Info", "unknown blacklist: " + name);
        }
        Blacklist blacklist = (Blacklist) results.get(0);
   
        if (blacklist.isBlacklisted(value)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
