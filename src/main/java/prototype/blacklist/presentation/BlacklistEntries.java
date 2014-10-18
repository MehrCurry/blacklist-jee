package prototype.blacklist.presentation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import prototype.blacklist.boundary.BlacklistEntryResource;
import prototype.blacklist.entity.BlacklistEntry;

/**
 * Bean for "faces/blacklistentries.xhtml". Provides client functionality for the {@link BlacklistEntryResource}.
 */
@Named("blacklistentries")
@RequestScoped
public class BlacklistEntries {

	private String type;
	private String value;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String getApplicationUri() {
		try {
			ExternalContext ext = FacesContext.getCurrentInstance()
					.getExternalContext();
			URI uri = new URI(ext.getRequestScheme(), null,
					ext.getRequestServerName(), ext.getRequestServerPort(),
					ext.getRequestContextPath(), null, null);
			return uri.toASCIIString();
		} catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	public List<String> getList() {
		Client client = ClientBuilder.newBuilder()
				.register(JacksonJsonProvider.class).build();
		WebTarget target = client.target(getApplicationUri()
				+ "/resources/blacklist/entries");
		JsonArray uris = target.request(MediaType.APPLICATION_JSON).get(
				JsonArray.class);
		List<String> entries = new ArrayList<String>();
		for (int i = 0; i < uris.size(); i++) {
			entries.add(uris.getString(i));
		}
		return entries;
	}

	public void add() {
		Client client = ClientBuilder.newBuilder()
				.register(JacksonJsonProvider.class).build();

		WebTarget target = client.target(getApplicationUri()
				+ "/resources/blacklist/entry");
		BlacklistEntry entry = new BlacklistEntry();
		entry.setType(type);
		entry.setValue(value);
		Entity<?> entity = Entity.json(entry);
		target.request().post(entity);
	}
}
