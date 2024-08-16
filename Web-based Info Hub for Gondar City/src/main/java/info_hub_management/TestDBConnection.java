package info_hub_management;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
	 public static void main(String[] args) {
	        try {
	            Connection conn = DBConnection.getConnection();
	            if (conn != null) {
	                System.out.println("Connected to the database successfully!");
	            } else {
	                System.out.println("Failed to connect to the database.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

}
