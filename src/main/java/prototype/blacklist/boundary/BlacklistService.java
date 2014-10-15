package prototype.blacklist.boundary;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("blacklist")
public class BlacklistService {
    
	@Context
	private UriInfo uri;
	
	private Map<String,Blacklist> blacklists;
	
	public BlacklistService() {
		blacklists = new HashMap<String,Blacklist>();		
	}
	
    @GET
    public Collection<String> getBlacklistOverview() {
    	Set<String> uris = new HashSet<String>(); 
    	for(String blacklistName : blacklists.keySet()){
    		uris.add(uri.getBaseUri()+"blacklist/"+blacklistName);
    	}
        return uris;
    }
    
    @GET
    @Path("{blacklistName}")
    public Blacklist getBlacklist(@PathParam("blacklistName") String blacklistName) {
        return blacklists.get(blacklistName);        
    }    
    
    @POST
    @Path("{blacklistName}")
    public Response addToBlacklist(@PathParam("blacklistName") String blacklistName, String ... blacklistEntries) {    	

    	if(!blacklists.containsKey(blacklistName)){
    		return Response.notModified().header("not.modified.reason", "The given blacklist name ["+blacklistName+"] does not exist").build();
    	} else {
    		Set<String> errors = new HashSet<String>();
    		for(String blacklistEntry : blacklistEntries){
    			if(!blacklistEntry.matches("^[A-Za-z0-9]+$")){
    				errors.add(blacklistEntry);
    			}
    		}
    		
    		if(errors.size() > 0){
    			return Response.notModified().header("not.modified.reason", "The given blacklist entry/entries ["+errors.toString()+"] is/are not alphanumeric").build();
    		}
    	}
    	
    	if(blacklistEntries.length == 1){
    		final URI id = URI.create("blacklist/"+blacklistName+"/"+blacklistEntries[0]);
    		blacklists.get(blacklistName).getListedElements().add(blacklistEntries[0]);
    		return Response.created(id).build();   	
    	}
    	
    	for(String blacklistEntry : blacklistEntries){
    		blacklists.get(blacklistName).getListedElements().add(blacklistEntry);        		    	      
		}
    	return Response.accepted().build();
	}
    
    @DELETE
    @Path("{blacklistName}/{blacklistEntry}")
    public Response deleteFromBlacklist(@PathParam("blacklistEntry") String blacklistEntry, @PathParam("blacklistName") String blacklistName) {    	
    	if(!blacklists.containsKey(blacklistName)){
    		return Response.notModified().header("not.modified.reason", "The given blacklist name ["+blacklistName+"] does not exist").build();
    	} else if(!blacklists.get(blacklistName).getListedElements().contains(blacklistEntry)){
    		return Response.notModified().header("not.modified.reason", "The given blacklist entry ["+blacklistEntry+"] does not exist").build();
    	}
    	
    	blacklists.get(blacklistName).getListedElements().remove(blacklistEntry);
    	return Response.accepted().build();
    }
    
    @PUT    
    public Response createBlacklist(Blacklist blacklist) {   
    	
    	if(blacklist.getListedElements() != null && blacklist.getListedElements().size() > 0){
    		Set<String> errors = new HashSet<String>();
    		for(String blacklistEntry : blacklist.getListedElements()){
    			if(!blacklistEntry.matches("^[A-Za-z0-9]+$")){
    				errors.add(blacklistEntry);
    			}
    		}
    		
    		if(errors.size() > 0){
    			return Response.notModified().header("not.modified.reason", "The given blacklist entry/entries ["+errors.toString()+"] is/are not alphanumeric").build();
    		}
    	}
    	
    	blacklists.put(blacklist.getName(), blacklist);
    	final URI id = URI.create("blacklist/"+blacklist.getName());
    	return Response.created(id).build();
    }        
}
