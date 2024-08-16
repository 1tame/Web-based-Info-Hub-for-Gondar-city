package info_hub_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static final String URL = "jdbc:mysql://localhost:3306/info_hub";
	private static final String USER = "root";  //we used to static to access the variables without Instantiation.
	private static final String PASSWORD = "root"; //final refers to that the values should not change after initialization.
	
	
	public static Connection getConnection() throws SQLException {
		
	
		
		return DriverManager.getConnection(URL,USER,PASSWORD);
	}
}
