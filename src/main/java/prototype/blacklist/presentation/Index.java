package prototype.blacklist.presentation;

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

/**
 * Created by jbeye on 15.10.14.
 */
@Named
@RequestScoped
public class Index {

    private String blacklistUrls;

    public void setBlacklistUrls(String blacklistUrls) {
        this.blacklistUrls = blacklistUrls;
    }

    public String getBlacklistUrls() {
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
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(getApplicationUri() + "/resources/blacklist");
        JsonArray response = target.request().get(JsonArray.class);
        blacklistUrls = String.valueOf(LocalDateTime.now()) + "<br/>";
        for (int i = 0; i < response.size(); i++){
            blacklistUrls += "<a href=\"" + response.getString(i) + "\">" + response.getString(i)+ "</a>";
            blacklistUrls += "<br/>";
        }
    }
}
