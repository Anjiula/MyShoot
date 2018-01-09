package string;

public class StringTest {

	public static void main(String[] args) {
		String string = "think in java";
		int a = string.indexOf("in");
		System.out.println(a);
		
		/**
		 * 从指定位置开始查找
		 */
		int b = string.indexOf("in", 0);
		System.out.println(b);
		
		
	}

}
