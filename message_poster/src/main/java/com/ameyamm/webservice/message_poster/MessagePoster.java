package com.ameyamm.webservice.message_poster;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ameyamm.model.MessageResource;
import com.ameyamm.model.dao.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Servlet for REST Web service
 */
@Path("/message-manager")
public class MessagePoster {

	private enum status {OK, ERROR};

	@POST 
    @Path("/post-message")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postMessage(String jsonString) {
    	Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    	
    	Message message = gson.fromJson(jsonString, Message.class);
        boolean success = MessageResource.postMessage(message);
        if (success)
        {
        	return gson.toJson(status.OK, status.class);
        }
        else 
        	return gson.toJson(status.ERROR, status.class);
    }

	@POST 
    @Path("/delete-message")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteMessage(String jsonString) {
    	Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    	
    	Message message = gson.fromJson(jsonString, Message.class);
    	
        boolean success = MessageResource.deleteMessage(message.getMessageId());
        if (success)
        {
        	return gson.toJson(status.OK, status.class);
        }
        else 
        	return gson.toJson(status.ERROR, status.class);
    }
	
    @GET
    @Path("/list-messages")
    @Produces(MediaType.APPLICATION_JSON)
    public String getList() {
    	Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create(); 
    	List<Message> messageList = MessageResource.getAllMessageList() ;
    	if (messageList == null)
    		return "No Messages";
    	return gson.toJson(messageList) ;
    }
    
    @GET
    @Path("/isPalindrome")
    @Produces(MediaType.APPLICATION_JSON)
    public String isMessagePalindrome(@QueryParam("messageId") String id ) {
    	
    	String messageString = MessageResource.getMessage(Long.parseLong(id));
    	JsonObject messagePalindromeState = new JsonObject();
    	messagePalindromeState.addProperty("message", messageString);
    	if (Message.isPalindrome(messageString)) {
    		messagePalindromeState.addProperty("is_palindrome", true);
    	}
    	else 
    		messagePalindromeState.addProperty("is_palindrome", false);
    	
    	Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    	
    	return gson.toJson(messagePalindromeState) ;
    }
}
