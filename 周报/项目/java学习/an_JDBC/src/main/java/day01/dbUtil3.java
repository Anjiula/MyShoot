package day01;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * jdbc 的封装
 * 
 * @author b_anhr
 *
 */
public class dbUtil3 {

	/**
	 * 封装4个参数
	 */
	static String urlString;
	static String userString;
	static String passwordString;
	static String drivString;
	
	/**
	 * properties  读取文件参数
	 */
	static {
		try {
		    
			Properties properties = new Properties();
			InputStream inputStream = dbUtil3.class.getClassLoader().getResourceAsStream("db.properties");
			properties.load(inputStream);
			
			urlString = properties.getProperty("jdbc.url");
			userString = properties.getProperty("jdbc.username");
			passwordString = properties.getProperty("jdbc.password");
			drivString = properties.getProperty("jdbc.driver");
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 封装一个静态方法提供connection
	 */
	public static Connection getConnection() {
		
		try {
			Class.forName(drivString);
			Connection connection = DriverManager.getConnection(urlString, userString, passwordString);
			
			return connection;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExceptionInInitializerError();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExceptionInInitializerError();
		}
	}

	
	/**
	 * 提供一个关闭数据库的方法
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 提供一个回滚的方法
	 */
	public static void rollBack(Connection connection) {
		if (connection != null) {
			try {
				connection.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
