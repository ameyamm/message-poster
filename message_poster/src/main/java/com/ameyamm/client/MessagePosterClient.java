package com.ameyamm.client;

import java.util.Calendar;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MessagePosterClient {

	public static void main(String [] args) {
		Client restClient = ClientBuilder.newClient();
		WebTarget webTarget = restClient.target("http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi");
		//WebTarget webTarget = restClient.target("http://localhost:8080/message_poster/webapi");
		WebTarget messageManager = webTarget.path("message-manager") ;

		testListMessages(messageManager) ;
		testDeleteMessage(messageManager);
		testMessagePoster(messageManager);
		testMessagePalindrome(messageManager);
	}

	private static void testListMessages(WebTarget servletTarget)
	{
		WebTarget listMessages = servletTarget.path("list-messages");

		Invocation.Builder invocationBuilder = listMessages.request();
		invocationBuilder.header("some-header", true) ;

		Response response = invocationBuilder.get();

		System.out.println(listMessages.getUri().getPath());
		System.out.println(response.readEntity(String.class));
	}

	private static void testMessagePoster(WebTarget servletTarget)
	{
		WebTarget postMessages = servletTarget.path("post-message") ;

		Random random = new Random(Calendar.getInstance().getTimeInMillis()); 

		String messageString = "{\"message\":\"Message " + random.nextLong() + "\", " +
				"\"user\" : " + random.nextLong() + "}" ;

		Response postResponse = postMessages.request().post(Entity.entity(messageString, MediaType.APPLICATION_JSON));
		System.out.println(postMessages.getUri().getPath());
		System.out.println(postResponse.readEntity(String.class));
	}

	private static void testMessagePalindrome(WebTarget servletTarget)
	{
		WebTarget palindromePath = servletTarget.path("isPalindrome")
				.queryParam("messageId", 3);

		Invocation.Builder invocationBuilder = palindromePath.request();
		invocationBuilder.header("some-header", true) ;

		Response response = invocationBuilder.get();

		System.out.println(palindromePath.getUri().getPath());
		System.out.println(response.readEntity(String.class));
	}

	private static void testDeleteMessage(WebTarget servletTarget)
	{
		WebTarget deleteMessagePath = servletTarget.path("delete-message");
		String messageString = "{\"messageId\":" + 5 + "}" ;

		Response postResponse = deleteMessagePath.request().post(Entity.entity(messageString, MediaType.APPLICATION_JSON));
		System.out.println(deleteMessagePath.getUri().getPath());
		System.out.println(postResponse.readEntity(String.class));
	}


}
