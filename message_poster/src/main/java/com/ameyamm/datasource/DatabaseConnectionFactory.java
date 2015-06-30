package com.ameyamm.datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.ameyamm.model.dao.Message;

/**
 * Connection Factory for managing database connections.
 * 
 * TODO: Implement a connection pool of database connections which manages the timeout 
 *       for the active connections and also prevent make and break of database connections 
 *       by reusing the created connections.
 * @author ameya
 */
public class DatabaseConnectionFactory   {

	private static Properties prop ;

	final static Logger logger = LogManager.getLogger(DatabaseConnectionFactory.class.getName());

	private DatabaseConnectionFactory() { }

	/**
	 * Returns an instance of Database Connection.
	 * @return connection instance to database
	 */
	public static DatabaseConnection getMessageDatabaseConnection() {
		if (prop == null) {
			prop = new Properties();

			try {
				prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			}
			catch (IOException exception) {
				logger.debug("Properties file not found");
				return null ;
			}
		}

		String host = prop.getProperty("host");
		String user = prop.getProperty("user") ;
		String password = prop.getProperty("password");
		String dbname = prop.getProperty("database");

		String url = "jdbc:postgresql://" + host + "/" + dbname;

		Properties dbProperties = new Properties();
		logger.debug("User" + user);
		dbProperties.setProperty("user", user);
		dbProperties.setProperty("password", password);

		return new DatabaseConnectionImpl(url, dbProperties);
	}

	/**
	 * Inner class provides an implementation for connecting to database
	 */
	private static class DatabaseConnectionImpl implements DatabaseConnection {

		private Connection connection ;  

		private DatabaseConnectionImpl( String url, Properties dbProperties) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, dbProperties) ;
			}  catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (SQLException e) {
				logger.debug("DB Connection error");
			}
		}

		@Override
		public List<Message> getMessageList() {
			List<Message> messageList = null ; 

			String query = "SELECT message_id, message, username, post_time from messages";

			if (connection == null )
				return null ; 

			try {
				PreparedStatement stmt = connection.prepareStatement(query) ;
				ResultSet rs = stmt.executeQuery() ;
				messageList = new ArrayList<Message>();
				while (rs.next()) {
					messageList.add(
							new Message(rs.getLong("message_id"),
									rs.getString("message"), 
									rs.getString("username"),
									rs.getTimestamp("post_time")));
				}

			} catch (SQLException e) {
				logger.debug("Error creating statement in getMessageList()");
				return null; 
			}

			return messageList ; 
		}

		@Override
		public boolean postMessage(Message message)
		{
			String sql = "insert into messages(message, username, post_time) "
					+ "values ( ?,?,? )";
			if (connection == null )
				return false ; 

			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, message.getMessage());
				stmt.setString(2, message.getUser());
				stmt.setTimestamp(3, new Timestamp(Calendar.getInstance().getTimeInMillis()) );
				int rows = stmt.executeUpdate();
				if (rows != 0)
					return true ; 
			} catch (SQLException e) {
				logger.debug("Error creating statement in getMessageList()");
				return false; 
			}
			return false ; 
		}

		@Override
		public String getMessage(long id)
		{
			String sql = "select message from messages where message_id = ?" ;

			String returnStr = null ; 

			if (connection == null )
				return null ; 

			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setLong(1, id);
				ResultSet rows = stmt.executeQuery();
				if(rows.next())
					returnStr = rows.getString("message");
			} 
			catch (SQLException e) {
				logger.debug("Error creating statement in getMessageList()");
			}

			return returnStr; 
		}

		@Override
		public boolean deleteMessage(long messageId) {
			String sql = "delete from messages "
					+ "where message_id = ?";
			if (connection == null )
				return false ; 

			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setLong(1, messageId);
				int rows = stmt.executeUpdate();
				if (rows != 0)
					return true ; 
			} catch (SQLException e) {
				logger.debug("Error executing delete message query");
				return false; 
			}

			return false ; 
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}
	}

	public static void main(String [] args)
	{
		DatabaseConnection connection = DatabaseConnectionFactory.getMessageDatabaseConnection();

		System.out.println(connection.getMessageList());
	}

}
