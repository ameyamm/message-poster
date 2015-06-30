package com.ameyamm.datasource;

import java.util.List;

import com.ameyamm.model.dao.Message;

/**
 * Interface for Database connection
 * @author ameya
 *
 */
public interface DatabaseConnection {

	/**
	 * Returns list of message objects
	 * @return list of messages
	 */
	List<Message> getMessageList();

	/**
	 * Adds the message to the database
	 * @param message : Message object to add
	 * @return true if successfully posted, false otherwise
	 */
	boolean postMessage(Message message);

	/**
	 * Returns the message for the given message id
	 * @param id id of the message
	 * @return returns the message string.
	 */
	String getMessage(long id);

	/**
	 * Deletes the message from the database
	 * @param id Identifier for the message
	 * @return true if message was successfully deleted, false otherwise
	 */
	boolean deleteMessage(long id);

	/**
	 * Closes database connection.
	 */
	void close();

}
