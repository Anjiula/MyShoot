package day01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 使用dbutil3封装的方法,执行查询语句
 * @author b_anhr
 *
 */
public class SelectDemo3 {

	public static void main(String[] args) {
		
		Connection connection = dbUtil3.getConnection();
		try {
			//1,连接数据库
			
			//2,创建statement
			Statement statement = connection.createStatement();
			//3,执行sql
			String sqlString = "SELECT * FROM STUDENT";
			
			ResultSet resultSet = statement.executeQuery(sqlString);
			//4,处理执行结果
			while (resultSet.next()) {
				System.out.println(resultSet.getString("SNO") + resultSet.getString("SNAME"));
			}
			
			//5,关闭
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil3.close(connection);
		}
	}

}
