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
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("blacklist")
public class BlacklistService {
    
	private Map<String,Blacklist> blacklists;
	
	public BlacklistService() {
		blacklists = new HashMap<String,Blacklist>();		
	}
	
    @GET
    public Collection<String> getBlacklistOverview() {
        return blacklists.keySet();
    }
    
    @GET
    @Path("{blacklistName}")
    public Blacklist getBlacklist(@PathParam("blacklistName") String blacklistName) {
        return blacklists.get(blacklistName);
    }
    
    @POST
    @Path("{blacklistName}")
    public Response addToBlacklist(String blacklistEntry, @PathParam("blacklistName") String blacklistName) {    	

    	if(!blacklists.containsKey(blacklistName)){
    		return Response.notModified().header("not.modified.reason", "The given blacklist name ["+blacklistName+"] does not exist").build();
    	}else if(!blacklistEntry.matches("^[A-Za-z0-9]+$")){
    		return Response.notModified().header("not.modified.reason", "The given blacklist entry ["+blacklistEntry+"] is not alphanumeric").build();
    	}
    	
    	blacklists.get(blacklistName).add(blacklistEntry);
		final URI id = URI.create("blacklist/"+blacklistName+"/"+blacklistEntry);
        return Response.created(id).build();    		    	       
    }
    
    @POST
    @Path("{blacklistName}")
    public Response addToBlacklist(String[] blacklistEntries, @PathParam("blacklistName") String blacklistName) {    	

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
    	
    	for(String blacklistEntry : blacklistEntries){
    		blacklists.get(blacklistName).add(blacklistEntry);                		    	      
		}
    	
    	return Response.accepted().build();    	
    }
    
    @DELETE
    @Path("{blacklistName}")
    public Response deleteFromBlacklist(String blacklistEntry, @PathParam("blacklistName") String blacklistName) {    	
    	if(!blacklists.containsKey(blacklistName)){
    		return Response.notModified().header("not.modified.reason", "The given blacklist name ["+blacklistName+"] does not exist").build();
    	}
    	
    	blacklists.get(blacklistName).remove(blacklistEntry);
    	return Response.accepted().build();
    }
    
    @PUT    
    public Response createBlacklist(Blacklist blacklist) {    	
    	blacklists.put(blacklist.getName(), blacklist);
    	final URI id = URI.create("blacklist/"+blacklist.getName());
    	return Response.created(id).build();
    }        
}
