package day02;

public class HomeWork {

	public static void main(String[] args) {
		String string = "120";
		
		/**
		 * 判断小数的正则
		 */
		String regex = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
		
		/**
		 * 判断整数的正则
		 */
		String regex2 = "^-?[1-9]\\d*$";
		
//		if (HomeWork.matchString(string, regex2)) {
//			//小数
//			double d = Double.parseDouble(string);
//			System.out.println("小数" + d);
//		}
		if (HomeWork.matchString(string, regex2)) {
			//整数
			int i = Integer.parseInt(string);
			System.out.println("整数" + i);
		}
		

	}
	
	/**
	 * 
	 * @param string	需要判断的字符串
	 * @param regex		正则表达式
	 * @return			0非   1是
	 */
	public static boolean matchString(String string, String regex) {
		if (string.matches(regex)) {
			return true;
		}
		return false;
	}
}
