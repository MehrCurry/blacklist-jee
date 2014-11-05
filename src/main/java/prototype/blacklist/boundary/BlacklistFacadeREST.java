/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import prototype.blacklist.entity.Blacklist;

@RestController
public class BlacklistFacadeREST extends AbstractFacade<Blacklist> {
    private static final String PATH = "/resources/blacklists";

    @PersistenceContext
    private EntityManager em;

    public BlacklistFacadeREST() {
        super(Blacklist.class);
    }

    @RequestMapping(value = PATH, method = RequestMethod.POST)
    @Override
    public void create(Blacklist entity) {
        super.create(entity);
    }

    @RequestMapping(value = PATH +"/{id}", method = RequestMethod.PUT)
    public void edit(@PathVariable Long id, Blacklist entity) {
        super.edit(entity);
    }

    @RequestMapping(value = PATH +"/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Long id) {
        super.remove(super.find(id));
    }

    @RequestMapping(value = PATH +"/{id}", method = RequestMethod.GET)
    public Blacklist find(@PathVariable Long id) {
        return super.find(id);
    }

    @RequestMapping(value = PATH, method = RequestMethod.OPTIONS)
    public Blacklist example() {
        final Blacklist bl = new Blacklist("example").addEntry("Test").addEntry("DE89 3704 0044 0532 0130 00");
        super.create(bl);
        return bl;
    }
    
    @RequestMapping(value = PATH, method = RequestMethod.GET)
    public List<Blacklist> findAll() {
        return super.findAll();
    }

    @RequestMapping(value = PATH +"/{from}/{to}", method = RequestMethod.GET)
    public List<Blacklist> findRange(@PathVariable Integer from, @PathVariable Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @RequestMapping(value = PATH +"/count", method = RequestMethod.GET)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
