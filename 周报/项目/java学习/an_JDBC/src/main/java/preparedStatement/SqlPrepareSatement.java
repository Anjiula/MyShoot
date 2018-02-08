package preparedStatement;
/**
 * 
 * 数据库连接池   
 * 
 * driverManager  管理数据库连接适用于单线程
 * 
 * 多线程时使用  dbcp  
 * 
 * 
 * prepareStatement     可以防止sql注入
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import day01.dbUtil3;

/**
 * sql 在db中翻译成"执行计划"才能去执行
 * 
 * 一条sql一个执行计划
 * 
 * java执行速度慢, sql语句完全一样(包括大小写和空格), 回重用"执行计划"
 * 
 * 
 * 
 * 结论: sql语句避免更改, 使用重用, 使用带参数的sql
 * 
 * @author b_anhr
 *
 */
public class SqlPrepareSatement {
	
	public static void main(String[] args) {
		Connection connection = dbUtil3.getConnection();
		try {
			
			/**
			 * dml  update
			 */
//			//1,创建preparedstatement 带上sql     (随即创建执行计划)
//			PreparedStatement pStatement = connection.prepareStatement("UPDATE STUDENT SET SNAME = ? WHERE SNO = ?");
//			
//			//2,加参数
//			pStatement.setString(1, "黎明");
//			pStatement.setString(2, "108");
//			
//			//3,执行计划
//			pStatement.executeUpdate();
			
			/**
			 * dml  insert
			 */
//			PreparedStatement pStatement = connection.prepareStatement("INSERT INTO STUDENT (SNO,SNAME,SSEX,CLASS,SBIRTHDAY) VALUES (?,?,?,?,?)");
//			
//			pStatement.setString(1, "110");
//			pStatement.setString(2, "张飞");
//			pStatement.setString(3, "男");
//			pStatement.setString(4, "95011");
//			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
//			pStatement.setDate(5, new java.sql.Date(sdFormat.parse("1998-11-27").getTime()));
//			
//			pStatement.executeUpdate();
			
			/**
			 * dml delete
			 */
//			PreparedStatement preparedStatement = connection.prepareStatement("DELETE STUDENT WHERE SNO = ?");
//			
//			preparedStatement.setString(1, "110");
//			
//			preparedStatement.executeUpdate();
			
			/**
			 * dql  select
			 */
			PreparedStatement pStatement2 = connection.prepareStatement("SELECT * FROM STUDENT WHERE SNAME LIKE ?");
						pStatement2.setString(1, "王%");
			
			ResultSet reSet = pStatement2.executeQuery();
			while (reSet.next()) {
				//GET...()方法也可以用序号,也可以用列名
				System.out.println(reSet.getString("SNO") + "," + reSet.getString("SBIRTHDAY") + "," + reSet.getString("SNAME"));
				/**
				 *	结果集元数据		ResultSetMetaData
				 *
				 *	可滚动结果集     但java不用,性能差
				 *
				 */
				ResultSetMetaData rSetMetaData = reSet.getMetaData();
				int count = rSetMetaData.getColumnCount();
				System.out.println(count);
				
				System.out.println(rSetMetaData.getColumnName(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbUtil3.close(connection);
		}        
		
	}
}
