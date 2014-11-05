/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import prototype.blacklist.control.BlacklistRepository;
import prototype.blacklist.entity.Blacklist;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
public class BlacklistFacadeREST {
    private static final String PATH = "/resources/blacklists";

    @Inject
    private BlacklistRepository repository;


    @RequestMapping(value = PATH, method = RequestMethod.POST)
    public void create(Blacklist entity) {
        repository.save(entity);
    }

    @RequestMapping(value = PATH + "/{id}", method = RequestMethod.PUT)
    public void edit(@PathVariable Long id, Blacklist entity) {
        entity.setId(id);
        repository.save(entity);
    }

    @RequestMapping(value = PATH + "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Long id) {
        repository.delete(id);
    }

    @RequestMapping(value = PATH + "/{id}", method = RequestMethod.GET)
    public Blacklist find(@PathVariable Long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = PATH, method = RequestMethod.OPTIONS)
    public Blacklist example() {
        final Blacklist bl = new Blacklist("example").addEntry("Test").addEntry("DE89 3704 0044 0532 0130 00");
        return repository.save(bl);
    }

    @RequestMapping(value = PATH, method = RequestMethod.GET)
    public List<Blacklist> findAll() {
        example();
        return repository.findAll();
    }

    @RequestMapping(value = PATH + "/count", method = RequestMethod.GET)
    public String countREST() {

        return String.valueOf(repository.count());
    }
}
