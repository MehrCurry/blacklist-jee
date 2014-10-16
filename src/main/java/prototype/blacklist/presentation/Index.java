package prototype.blacklist.presentation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.time.LocalDateTime;

/**
 * Created by jbeye on 15.10.14.
 */
@Named
@RequestScoped
public class Index {

    public void setHello(String hello) {
        this.hello = hello;
    }

    public String getHello() {
        return hello;
    }

    String hello = "Hallo";

    @PostConstruct
    public void init(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/blacklist-jee7/resources/blacklist");
        JsonArray response = target.request().get(JsonArray.class);
        hello = String.valueOf(LocalDateTime.now()) + "<br/>";
        for (int i = 0; i < response.size(); i++){
            hello += "<a href=\"" + response.getString(i) + "\">" + response.getString(i)+ "</a>";
            hello += "<br/>";
        }
    }
}
