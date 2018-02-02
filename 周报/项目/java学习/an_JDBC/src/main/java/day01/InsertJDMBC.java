package day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 按照demo1的步骤insert一条数据
 * 
 * @author b_anhr
 *
 */
public class InsertJDMBC {

	public static void main(String[] args) {
		
		/*
		 * 1,导入驱动
		 */
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			
			/*
			 * 2,连数据库  (创建connection对象)
			 */
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@172.16.51.88:1521:orcl", "system", "Sun.japan");
			
			System.out.println("success");
			
			/*
			 * 3,执行sql语句
			 */
			Statement statement = connection.createStatement();
			
//			String dmlString = "INSERT INTO RAIN_DEMO VALUES(12,'bob')";
			String dmlString = "UPDATE RAIN_DEMO SET DEMO_NAME = 'AAAAAAAAA' WHERE DEMO_ID = 12";
			
			int flag = statement.executeUpdate(dmlString);
			
			/*
			 * 4,处理返回结果
			 */
			System.out.println(flag);
			
			
			/*
			 * 5,关闭数据库
			 */
			connection.close();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
