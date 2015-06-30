# message-poster : Repository for REST Web Service which : 
#                  1) Allows users to post messages
#                  2) list all messages posted by all users
#                  3) Delete specific messages
#                  4) Select a particular message and check if it is a palindrome

Architecture:
There are basically 3 modules in this REST Web Service:
1) The Controller Module manages and process GET/POST requests (com.ameyamm.webservice.message_poster.MessagePoster)
2) The Database Manager module (com.ameyamm.datasource.DatabaseConnectionFactory) which talks to the database and contains code specific to the database. It follows a Factory design pattern which provides static method to get connection objects. 
3) The Intermediary MessageResource module (com.ameyamm.model.MessageResource) acts as an intermediary between the controller and database module. 
4) Database module which is the actual database. I have use PostGreSQL as the database which resides on a different EC2 instance.

                            Controller <----> MessageResource <-----> Database Manager

Building the app:
1) This application can be imported directly into eclipse.
2) It is a maven project, so the pom.xml included in the project should take care of downloading the necessary dependent libraries :
    2.1) jersey : Library for REST web services.
    2.2) Gson : Google's library for JSON management.
3) To build the war file for the webapp, use the maven goal: "clean install"
4) Then deploy the war file on a Web Server (I have used apache tomcat)

Accessing the App:
1) The web service is deployed on Amazon EC2 instance and can be access using:
      http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi
2) To access various features :
    2.1) List messages :
	     		http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi/message-manager/list-messages
	  2.2) Palindrome Check for a message:
		      http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi/message-manager/isPalindrome?messageId={id}
    2.3) Post a message using a JSON string in POST request: 
	        http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi/message-manager/post-message 
		  		JSON Message : { "message" : "{message}", "user" : "{user}" }
	  2.4) Delete a message using a JSON string in a POST request: 
	        http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi/message-manager/delete-message
		      JSON Message : { "messageId" : "{id}" }
		      
PS: I have included a REST client code (com/ameyamm/client/MessagePosterClient) which I used to do some basic testing of the REST Web Service
    
