package prototype.blacklist.boundary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

@Controller
public class HealthCheck {
  @RequestMapping(value = "/resources/health", method = RequestMethod.GET)
  public String check() {
    return "I am alive " + LocalDateTime.now();
  }
}
