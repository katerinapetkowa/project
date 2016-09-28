package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static DBManager instance;
	private Connection connection;
	
	private static final String DB_IP = "127.0.0.1";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "9gag";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "root";
	private static final String URL = "jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_NAME;
	
	private DBManager(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static DBManager getInstance(){
		if(instance == null){
			instance = new DBManager();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
}
