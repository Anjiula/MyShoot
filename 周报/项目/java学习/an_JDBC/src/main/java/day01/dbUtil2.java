package day01;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class dbUtil2 {
	
	static String jdbcDriverString;
	static String userString;
	static String passwordString;
	static String urlString;
	
	static{
		try {
			//封装4个参数
			
			//1,创建property对象
			Properties properties = new Properties();
			
			//2,获取property内容
			InputStream inStream = dbUtil2.class.getClassLoader().getResourceAsStream("db.properties");
			properties.load(inStream);
			
			System.out.println(properties);
			//3,读取文件内容
			jdbcDriverString = properties.getProperty("jdbc.driver");
			userString = properties.getProperty("jdbc.username");
			passwordString = properties.getProperty("jdbc.password");
			urlString = properties.getProperty("jdbc.url");
			
			//4.关闭
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static Connection getConnection() {
		
		try {
			Class.forName(jdbcDriverString);
			
			Connection connection = DriverManager.getConnection(urlString, userString, passwordString);
			
			
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExceptionInInitializerError();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExceptionInInitializerError();
		}
	}
	
	/**
	 * 对关闭数据库封装
	 * @param connection
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
//	public static void main(String[] args) {
//		
//		System.out.println(jdbcDriverString);
//		System.out.println(userString);
//		System.out.println(passwordString);
//		System.out.println(urlString);
//
//	}

}
