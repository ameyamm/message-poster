<html>
<body>
	<h2>Message Poster REST Webservice</h2>
	<h3>
	List messages :
	</h3>
	<p>
		http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi/message-manager/list-messages
	</p>

	<h3>
	Palindrome Check for a message:
	</h3>
	<p>
		"http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi/message-manager/isPalindrome?messageId={id}"
	</p>
	<h3>
		Post a message using a JSON string in POST request: 
	</h3>
	<p>
		http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi/message-manager/post-message 
		<br> 
		JSON Message : { "message" : "{message}", "user" : "{user}" }
	</p>
	<h3>
		Delete a message using a JSON string in a POST request: 
	</h3>
	<p>
		http://ec2-52-26-0-8.us-west-2.compute.amazonaws.com:8080/message_poster/webapi/message-manager/delete-message
		<br> 
		JSON Message : { "messageId" : "{id}" }
	</p>

</body>
</html>
