package day01;
/**
 * 文档注释
 * 文档注释可以对类，方法，常量进行修饰  （属性不需要加文档注释）
 * 文档注释最终可以被javadoc命令所解释，然后执行
 * 
 * 
 * 生成一个文档手册
 * @author b_anhr
 * @version 1.0  日/月/年
 * @see  参考
 * @since JDK1.0   从JDK1.0版本就可以用   （特性不会向下兼容）
 *
 */
public class APIDocDemo {
	
	/**
	 * 常量的定义
	 * sayhello方法中用到的问候语
	 */
	public static final String INFO = "你好";
	
	/**
	 * 为制定的用户添加一个问候语
	 * @param name		
	 * @return
	 */
	public static String sayHello(String name) {
		return INFO + name;
	}
	
	
}
