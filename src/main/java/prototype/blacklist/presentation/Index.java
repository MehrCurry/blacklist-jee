package prototype.blacklist.presentation;


import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.faces.FacesException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jbeye on 15.10.14.
 */
@Named
@RequestScoped
public class Index {

    private List<String> blacklistUrls;       
    
    private Client client;

    private WebTarget target;

    public List<String> getBlacklistUrls() {
        this.client = ClientBuilder.newBuilder().build();
        this.target = this.client.target(getApplicationUri() + "/resources/blacklist");

        GenericType<Collection<String>> type = new GenericType<Collection<String>>(){};
        Collection<String> col = this.target.request().get(type);
        blacklistUrls = new ArrayList<>(col);
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
            throw new FacesException(e);
        }
    }

    public void delete(String url){
        this.client = ClientBuilder.newClient();
        this.target = this.client.target(url);
        this.target.request().delete();
    }

}
