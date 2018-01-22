package day02;

/**
 * 字符串正则表达式2
 * @author b_anhr
 * 
 * 图片重命名
 */
public class Test02 {

	public static void main(String[] args) {
		String string = "1.jpg";
		
		String string2[] = string.split("\\.");
		/**
		 * System.currentTimeMillis()
		 * 系统当前时间
		 */
		string = System.currentTimeMillis()+"." + string2[1];
		
		System.out.println(string);
		
		
		Point point = new Point(1, 3);
		String string3 = point.toString();
		System.out.println(string3);
		
		Point point2 = new Point(1, 3);
		System.out.println(point == point2);
		/**
		 * 如果没有重写equals 用到的是object的equals方法，相等于==
		 */
		System.out.println(point.equals(point2));
		/**
		 * equal  内容
		 * 
		 * ==  地址
		 */
		
	}

}
