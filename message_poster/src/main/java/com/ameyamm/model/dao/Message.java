package com.ameyamm.model.dao;

import java.util.Date;
/**
 * POJO object for Message entity
 * @author ameya
 *
 */
public class Message {
	private long messageId ; 
	private String message ; 
	private String user ;
	private Date postTime;
	
	public Message() {
		this.messageId = 0;
		this.message = null ; 
		this.user = null ; 
		this.postTime = null ; 
	}
	
	public Message(long id, String message, String user, Date postTime) {
		super();
		this.messageId = id ; 
		this.message = message;
		this.user = user;
		this.postTime = postTime;
	}
	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long id) {
		this.messageId = id;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public static boolean isPalindrome(String message) {
		if (message == null)
			return false ; 
		
		int start = 0 ; 
		int end = message.length() - 1;
		
		while (start <= end)
		{
			if (message.charAt(start) != message.charAt(end))
				return false ; 
			start++;
			end--;
		}
		return true ;
	}
}
