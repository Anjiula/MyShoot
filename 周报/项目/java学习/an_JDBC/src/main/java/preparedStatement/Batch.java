package preparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;

import day01.dbUtil3;

/**
 * addBatch(String sql)
 * executeBatch()
 * addBatch()
 * clear()
 * 
 * 批量参数处理
 * 
 * @author b_anhr
 *
 */
public class Batch {

	public static void main(String[] args) {

		String sqlString = "INSERT INTO STUDENT (SNO,SNAME,SSEX,CLASS,MONERY) VALUES (?,?,?,?,?)";
		
		Connection connection = null;
		
		try {
			connection = dbUtil3.getConnection();
			
			//取消自动提交
			connection.setAutoCommit(false);
			
			PreparedStatement pStatement = connection.prepareStatement(sqlString);
			
			for (int i = 0; i < 100; i++) {
				
				pStatement.setString(1, Integer.valueOf(i).toString());
				
				pStatement.setString(2, "lim" + Integer.valueOf(i).toString());
				
				pStatement.setString(3, (i%2 == 0) ? "男" : "女");
				
				pStatement.setString(4, (i%2 == 0) ? "99999" : "88888");
				
				pStatement.setInt(5, i * 100);
				//加入缓存区
				pStatement.addBatch();
				
				pStatement.executeBatch();
				
				System.out.println("wancheng ");
				connection.commit();
			}
			pStatement.executeQuery();
		} catch (Exception e) {
			dbUtil3.rollBack(connection);
			e.printStackTrace();
		} finally {
			dbUtil3.close(connection);
		}

	}

}
