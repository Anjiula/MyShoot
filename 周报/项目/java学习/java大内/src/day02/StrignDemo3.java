package day02;

/**
 * 字符串正则表达式3(网络用语和谐)
 * @author b_anhr
 * 
 * replaceAll();
 * 满足正则表达式的部分替换为给定字符串
 *
 */

/**
 * 该类继承了object类（所欲的类直接/间接的继承object）
 * @author b_anhr
 *
 */
public class StrignDemo3 {

	public static void main(String[] args) {
		String string = "aafadn12341234jlk";
		
		string = string.replaceAll("\\d+", "#NUMBER#");

		System.out.println(string);
		
		
		String regex = "(wcnm|nm|mmp)";
		String string2 = "我wcnmnm";
		
		string2 = string2.replaceAll(regex, "***");

		System.out.println(string2);
		
		Test02 t = new Test02();
		t.toString();
	}

}
