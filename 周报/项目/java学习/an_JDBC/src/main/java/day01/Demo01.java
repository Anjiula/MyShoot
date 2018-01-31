package day01;
/**
 * 1,导入jdbc驱动包jar  (可以使用maven,也可以在官网导入)
 * 
 * 
 * 2,注册驱动
 * 		class.forName(驱动程序类名)
 * 
 * 3,创建connection对象	连接数据库()
 * 		drivermanager.getconnection(url,user,password)
 * 
 * 4,创建statement对象,
 * 		conn.getStatement() 创建
 * 		用于执行sql语句
 * 		excute(ddl)  常用于执行ddl												return  boolean (表示有无结果集)
 * 		excuteUpdate(dml) zhixing dml 语句,  insert(),update(),delete()			return  number  (表示更新数量)
 * 		excuteQuery(dql) 		select()										return 	resultset  (二维查询结果,使用for遍历)
 * 
 * 5,处理执行结果
 * 
 * 6,关闭连接
 * 		
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo01 {

	public static void main(String[] args) {
		
		try {
			//注册驱动
			Class.forName("oracle.jdbc.OracleDriver");
			
			String url = "jdbc:oracle:thin:@172.16.51.88:1521:orcl";
			String user = "system";
			String password = "Sun.japan";
			
			//连接数据库
			Connection connection = DriverManager.getConnection(url, user, password);
			
			//输出connection 对象的实际类型
			//驱动程序提供了connection接口的实现类
			System.out.println(connection.getClass());
			
			//Statement   接口  驱动提供实例
			Statement statement = connection.createStatement();
			
			//执行sql
			String ddl = "create table Rain_demo" + "(DEMO_ID number(7)," + "DEMO_NAME varchar2(100))";
			//返回true  : 有结果集    (不是创建成功)
			//返回false : 没有结果集
			//创建失败: 抛异常  
			boolean b = statement.execute(ddl);
			System.out.println(b);
			
			//关闭连接
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
