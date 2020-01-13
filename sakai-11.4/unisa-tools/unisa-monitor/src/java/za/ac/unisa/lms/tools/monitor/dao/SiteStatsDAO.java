package za.ac.unisa.lms.tools.monitor.dao;

import java.sql.*;

public class SiteStatsDAO
{
	public SiteStatsDAO(){}
	
	public Connection dbConnect(String dbConnectStr,String dbUser,String dbPasswd)
	{
		try
		{
			String driverName = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection(dbConnectStr,dbUser,dbPasswd);
			System.out.println("Connected");
			return conn;
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	}
}
