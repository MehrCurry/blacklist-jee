package prototype.blacklist.presentation;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbeye on 15.10.14.
 */
@Named
@RequestScoped
public class Index {

    private List<String> blacklistUrls;

    private Client client;

    private WebTarget target;

    public void setBlacklistUrls(List<String> blacklistUrls) {
        this.blacklistUrls = blacklistUrls;
    }

    public List<String> getBlacklistUrls() {
        return blacklistUrls;
    }

    /**
     * Determine the applicationUri.
     * @return the applicationUri.
     */
    private String getApplicationUri() {
        try {
            ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null, ext.getRequestServerName(), ext.getRequestServerPort(),
                    ext.getRequestContextPath(), null, null);
            return uri.toASCIIString();
        } catch (URISyntaxException e) {
            return "http://localhost:8080/blacklist-jee7/";
        }
    }

    @PostConstruct
    public void init(){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(getApplicationUri() + "/resources/blacklist");
        JsonArray response = this.target.request().get(JsonArray.class);
        blacklistUrls = new ArrayList<>();
        for (int i=0; i<response.size(); i++) {
            blacklistUrls.add(response.getString(i) );
        }
    }

    public void delete(String url){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(url);
        this.target.request().delete();
        System.out.println(url);
    }

}
