package preparedStatement;
/**
 * 事务处理    		jdbc事务API
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import day01.dbUtil3;

public class Event {

	/**
	 * 交易汇款的方法
	 */
	public static void transaction(String from,String to,Integer amount) {
		//余额不足,抛出异常 ,模拟sno字段为余额
		String sql = "UPDATE STUDENT SET MONERY = MONERY + ? WHERE SNAME = ?";
		String sql3 = "SELECT MONERY FROM STUDENT WHERE SNAME = ?";	
		
		Connection connection2 = null;
		
		try {
			connection2 = dbUtil3.getConnection();
			
			//取消自动提交,后续手动提交
			connection2.setAutoCommit(false);
			
			//业务处理	: 转账汇款,如果余额不足,回滚操作.抛出异常
			//减钱
			PreparedStatement pStatement = connection2.prepareStatement(sql);
			pStatement.setInt(1, -amount);
			pStatement.setString(2, from);
			int i = pStatement.executeUpdate();
			if (i != 1) {
				throw new Exception("扣钱有误");			//一抛异常就回滚回去
			}
			//释放资源, 重用pStatement
			pStatement.close();

			
			//加钱
			pStatement = connection2.prepareStatement(sql);
			pStatement.setInt(1, amount);
			pStatement.setString(2, to);
			
			int i2 = pStatement.executeUpdate();
			if (i2 != 1) {
				throw new Exception("汇款有误");
			}
			//重用变量
			pStatement.close();
			
			
			//检查原账户是否透支   如果透支, 抛出异常
			pStatement = connection2.prepareStatement(sql3);
			pStatement.setString(1, from);
			ResultSet reSet  = pStatement.executeQuery();		
			while (reSet.next()) {
				int monery = reSet.getInt(1);
				if (monery < 5) {
					System.out.println(from + "所剩余额不足5元");
				}
				if (monery < 0) {
					dbUtil3.rollBack(connection2);
					throw new Exception("余额不足,汇款失败");
				}
			}
			
			//生效
			connection2.commit();
			System.out.println("汇款成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			//抛出异常立即回滚
//					connection2.rollback();
//					if (connection2 != null) {
//						try {
//							connection2.rollback();
//						} catch (Exception e2) {
//							e.printStackTrace();
//						}
//					}
			dbUtil3.rollBack(connection2);
		} finally{
			dbUtil3.close(connection2);
		}
	}
	
	public static void main(String[] args) {
		transaction("匡明","黎明", 100);
	}

}
