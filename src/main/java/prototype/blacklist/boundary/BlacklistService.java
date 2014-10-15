package prototype.blacklist.boundary;

import java.net.URI;
import java.util.*;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;


/**
 * Simple service to manage in-mem blacklists via REST. 
 * 
 * Request/Response handling refers to: http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html.
 */
@ApplicationScoped
@Path("blacklist")
public class BlacklistService {
    
	/** The URI of this service. (e.g. http://localhost:8080/blacklist-jee7/resources) */
	@Context
	private UriInfo uri;
		
	private Map<String,Blacklist> blacklists;
	
	public BlacklistService() {
		blacklists = new HashMap<String,Blacklist>();		
	}
	
	/**
	 * Returns a collection of URIs to the stores blacklists and thus 
	 * works as an entry point of the service.
	 */
    @GET
    public Collection<String> getBlacklistOverview() {
    	Set<String> uris = new HashSet<>();
    	for(String blacklistName : blacklists.keySet()){
    		uris.add(uri.getBaseUri()+"blacklist/"+blacklistName);
    	}
        return uris;

    }
    
    /**
     * Get a specific blacklist by name.
     * 
     * @param blacklistName The name of the blacklist
     * @return The blacklist connected to the name or null.
     */    
    @GET
    @Path("{blacklistName}")
    public Blacklist getBlacklist(@PathParam("blacklistName") String blacklistName) {
        return blacklists.get(blacklistName);        
    }    
    
    /**
     * Puts one or more blacklist entries into a specific blacklist.
     * 
     * If the blacklist does not exist a not found response will be sent. If on of the blacklistEntries is malformed
     * a bad request response is given.
     * 
     * If a single entry is given a created response will be returned, containing the URI to the
     * new entry. If a list of entries is given a simple ok response will be sent.
     * 
     * @param blacklistName The name of the blacklist
     * @param blacklistEntries One or more blacklist entries.
     * 
     * @return A response containing informations about the success of the operation.
     */    
    @POST
    @Path("{blacklistName}")
    public Response addToBlacklist(@PathParam("blacklistName") String blacklistName, String ... blacklistEntries) {    	

    	if(!blacklists.containsKey(blacklistName)){
    		return Response.status(Status.NOT_FOUND).build();
    	} else {
    		Set<String> errors = new HashSet<String>();
    		for(String blacklistEntry : blacklistEntries){
    			if(!blacklistEntry.matches("^[A-Za-z0-9]+$")){
    				errors.add(blacklistEntry);
    			}
    		}
    		
    		if(errors.size() > 0){
        		// see standard RFC 7231
    			return Response.status(Status.BAD_REQUEST).header("Validation-Problem", "The given blacklist entry/entries "+errors.toString()+" is/are not alphanumeric").build();    		    			
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
    	return Response.ok().build();
	}
    
    /**
     * Returns a not modified, no content or okay response depending on the request.
     * 
     * If the given blacklist name is unknown a not modified response is sent, containing the information
     * that the blacklist is unknown in its not.modified.reason header.
     * 
     * If the requested blacklist entry does not exist the no content response is sent. Else a simple ok 
     * response will be returned.
     * 
     * @param blacklistEntry The identifier of the blacklist entry
     * @param blacklistName Tha name of the blacklist
     * 
     * @return A HTTP response.
     */
    @GET
    @Path("{blacklistName}/{blacklistEntry}")
    public Response getBlacklistEntry(@PathParam("blacklistEntry") String blacklistEntry, @PathParam("blacklistName") String blacklistName) {
    	if(!blacklists.containsKey(blacklistName)){
    		// see standard RFC 7231
			return Response.status(Status.BAD_REQUEST).header("validation-problem", "The given blacklist name ["+blacklistName+"] does not exist").build();    		
    	} else if(!blacklists.get(blacklistName).getListedElements().contains(blacklistEntry)){
    		return Response.noContent().build();
    	}
    	return Response.ok().build();
    }   
    
    /**
     * Deletes a requested blacklist entry from a blacklist if both exist.
     * 
     * If the given blacklist or the blacklist entry does not exist a not found response is sent. 
     * Else, the entry will be removed from the list and a no content response is sent.
     * (see http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)
     * 
     * @param blacklistEntry The identifier of the blacklist entry
     * @param blacklistName The name of the blacklist
     * 
     * @return A HTTP response.
     */
    @DELETE
    @Path("{blacklistName}/{blacklistEntry}")
    public Response deleteFromBlacklist(@PathParam("blacklistEntry") String blacklistEntry, @PathParam("blacklistName") String blacklistName) {    	
    	if(!blacklists.containsKey(blacklistName)){
    		return Response.status(Status.NOT_FOUND).build();
    	} else if(!blacklists.get(blacklistName).getListedElements().contains(blacklistEntry)){
    		return Response.status(Status.NOT_FOUND).build();
    	}
    	
    	blacklists.get(blacklistName).getListedElements().remove(blacklistEntry);
    	return Response.noContent().build();
    }
    
    /**
     * Creates the given blacklist and sends a created response if all given blacklist entries 
     * match the blacklist entry format. Else a bad request header is returned containing informations
     * about malformed entries in its header.
     * 
     * @param blacklist A blacklist given by the request. (e.g. in the body as JSON which depends on the client)
     * @return A HTTP response.
     */
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
            	// see standard RFC 7231
    			return Response.status(Status.BAD_REQUEST).header("validation-problem", "The given blacklist entry/entries "+errors.toString()+" is/are not alphanumeric").build();
    		}
    	}
    	
    	blacklists.put(blacklist.getName(), blacklist);
        final URI id = URI.create("blacklist/"+blacklist.getName());
    	return Response.created(id).build();
    }  
    
    /**
     * Deletes a requested blacklist if it exist.
     * 
     * If the given blacklist does not exist a not found response is sent. 
     * Else, the blacklist will be removed and a no content response is sent 
     * (see http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html)
     * 
     * @param blacklistEntry The identifier of the blacklist entry
     * @param blacklistName The name of the blacklist
     * 
     * @return A HTTP response.
     */
    @DELETE
    @Path("{blacklistName}")
    public Response deleteBlacklist(@PathParam("blacklistName") String blacklistName) {    	
    	if(!blacklists.containsKey(blacklistName)){
    		return Response.status(Status.NOT_FOUND).build();
    	} 
    	
    	blacklists.remove(blacklistName);
    	return Response.noContent().build();
    }
}
