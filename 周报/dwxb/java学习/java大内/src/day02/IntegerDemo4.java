package day02;
/**
 * 包装类提供了一个静态方法：parseXXX（string str）
 * 
 * 可以将提供的字符串转换为对应的基本数据类型。
 * 
 * 前提是该字符串必须正确的描述基本类型可以保存的值
 * 
 * @author b_anhr
 *
 */
public class IntegerDemo4 {

	public static void main(String[] args) {
		String string = "123";
		int s = Integer.parseInt(string);
		
		System.out.println(s);
		
		String string2 = "123.123";
		
		double d = Double.parseDouble(string2);
		
		System.out.println(d);
	}

}
