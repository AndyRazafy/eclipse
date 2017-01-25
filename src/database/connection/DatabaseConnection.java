package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection 
{
	public static Connection getOracleConnection(String username, String password)throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);
		return conn;
	}
	
	public static Connection getPostgreConnection(String databaseName, String username, String password)throws Exception{
		Class.forName("org.postgresql.Driver");
		Connection conn = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName,
				username, password);
		return conn;
	}
}
