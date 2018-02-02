package day01;
/**
 * executeQuery  返回resultset  遍历,  取出表中数据
 * 
 * 
 * 
 * 第六步,连接关闭不可靠,抛异常的时候回跳过关闭,,.....
 * 
 * 
 * 正确写法应该是吧关闭代码放到finally中
 * Conniction conn = null;
 * try{
 * }catch(Exception e){
 * 		e.print...
 * }finally{
 * 		//sql关闭本身也要trycatch
 * 		//如果conn为null 会 报空指针异常
 * 		if(conn!=null){
 * 			conn.close();
 * 		}
 * }
 * 
 * 
 * 
 * Conniction conn = null;
 * try{
 * }catch(Exception e){
 * 		e.print...
 * }finally{
 * 		//可以将那一段代码封装到dbutils中
 * 		dbutil2.close(conn);
 * }
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SelectJDBC {

	public static void main(String[] args) {
		
		try {
			//1,注册驱动
//			Class.forName("oracle.jdbc.OracleDriver");
//			
//			//2,连接数据库
//			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:172.16.51.88:1521:orcl", "system", "Sun.japan");
			
			Connection connection = dbUtil2.getConnection();
			
			//3,创建statemen
			Statement statement = connection.createStatement();
			
			//4,执行sql
			String sqlString = "SELECT * FROM STUDENT";
			
			//5,处理结果
			ResultSet resultSet = statement.executeQuery(sqlString);
			while (resultSet.next()) {
				String sno = resultSet.getString("SNO");
				String snameString = resultSet.getString("SNAME");
				String ssexString = resultSet.getString("SSEX");
				String sbirthdayString = resultSet.getString("SBIRTHDAY");
				String classString = resultSet.getString("CLASS");
				System.out.println("SNO :" + sno + " SNAME :" +snameString + " SSEX :" + ssexString + " SBITTHDAY :" + sbirthdayString + " CLASS :" + classString);
			}
			/**
			 * 两个close()方法,再关闭数据库,两个会自动关闭
			 * 
			 * java 是建议关闭的, 在有开了很多个resultset,statemen 的,要及时关闭, 少的话,就不用了
			 */
			resultSet.close();
			statement.close();
			
			//6,关闭数据库
//			connection.close();
			
			//工作中应当这样关闭数据库
			try {
				dbUtil2.close(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
