package com.vehicle;
import java.sql.*;

public class DbConnection {
	private static Connection conn=null;

	
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		try
		{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		    conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","MYDB10","MYDB10");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return conn;

	}
}
