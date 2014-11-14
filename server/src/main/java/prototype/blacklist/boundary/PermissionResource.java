/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.blacklist.boundary;

import org.springframework.web.bind.annotation.*;
import prototype.blacklist.control.BlacklistRepository;
import prototype.blacklist.entity.Blacklist;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class PermissionResource {

  @Inject
  private BlacklistRepository repository;

  public void setRepository(BlacklistRepository repository) {
    this.repository = repository;
  }

  @RequestMapping(value = "/permission/{name}/{value}", method = RequestMethod.GET)
  public void isGranted(@PathVariable String name, @PathVariable String value, HttpServletResponse response) {

    Optional<Blacklist> candidate = repository.findByName(name).stream().findFirst();
    if (!candidate.isPresent()) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else if (candidate.get().isBlacklisted(value)) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    } else {
      response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
  }

  @RequestMapping(value = "/permission/{name}", method = RequestMethod.POST)
  public void block(@PathVariable String name, @RequestBody String value) {

    Optional<Blacklist> candidate = repository.findByName(name).stream().findFirst();
    if (candidate.isPresent()) {
      repository.save(candidate.get().addEntry(value));
    }
  }

  @RequestMapping(value = "/permission/{name}", method = RequestMethod.DELETE)
  public void unblock(@PathVariable String name, @RequestBody String value) {

    Optional<Blacklist> candidate = repository.findByName(name).stream().findFirst();
    if (candidate.isPresent()) {
      repository.save(candidate.get().removeEntry(value));
    }
  }

}
