package preparedStatement;
/**
 * 
 * getgeneratedkeys()   略
 * 
 * 分页查询     preparestatement     select  where .. between ? and ?
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;

import day01.dbUtil3;

/**
 * addBatch(String sql)
 * executeBatch()
 * addBatch()
 * clear()
 * 
 * 批量参数处理    	批量执行sql    分批执行
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
				
				//可以分批执行
				if (i%10 == 0) {
					//执行
					pStatement.executeBatch();
					pStatement.clearBatch();
					System.out.println("wancheng" + i);
				}
			}
			//这里处理分批执行时剩在缓存区的一些sql
			pStatement.executeBatch();
			//手动提交
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			dbUtil3.rollBack(connection);
		} finally {
			dbUtil3.close(connection);
		}
	}

}
