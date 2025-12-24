package hospital_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	static String URL="jdbc:postgresql://localhost:5432/hospital_application";
	static String username="postgres";
	static String password="pass@123";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(URL,username,password);
		return con;
	}
	
	public static void closeConnection(Connection con) throws SQLException {
		con.close();
	}
}