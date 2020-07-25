package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.xml.DOMConfigurator;

public class ConnectionUtils {

	private static String USER_NAME = "root";
	private static String USER_PASSWORD = "lendyo1991";
	private static String URL = "jdbc:mysql://localhost/i_shop?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
	
	public static Connection openConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		DOMConfigurator.configure("loggerConfig.xml");
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		return DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD);
	}
	
}
