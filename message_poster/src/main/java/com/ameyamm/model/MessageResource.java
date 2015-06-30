package com.ameyamm.model;

import java.util.List;

import com.ameyamm.datasource.DatabaseConnection;
import com.ameyamm.datasource.DatabaseConnectionFactory;
import com.ameyamm.model.dao.Message;

public class MessageResource {
	public static List<Message> getAllMessageList() {
		DatabaseConnection connection = DatabaseConnectionFactory.getMessageDatabaseConnection();
		List<Message> messageList = connection.getMessageList();
		connection.close();
		return messageList ; 
	}
	
	public static boolean postMessage(Message message)	{
		
		if (message == null)
			return false ; 
		
		DatabaseConnection connection = DatabaseConnectionFactory.getMessageDatabaseConnection();
		return connection.postMessage(message) ;
	}
	
	public static String getMessage(long id) {
		DatabaseConnection connection = DatabaseConnectionFactory.getMessageDatabaseConnection();
		String messageStr = connection.getMessage(id);
		return messageStr ; 
	}
	
	public static boolean deleteMessage(long id) {
		DatabaseConnection connection = DatabaseConnectionFactory.getMessageDatabaseConnection() ; 
		return connection.deleteMessage(id);
	}
}
