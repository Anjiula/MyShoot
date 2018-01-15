package regularExpression;

public class RegularExpression {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String string = "\\";
		System.out.print(string);
		
		/**
		 * [a-z]?		
		 * [a-z]+
		 * [a-z]{}
		 */
		/**
		 * 1、由于名称是由若干个字母、数字、下划线和中划线组成，所以需要用到+表示多次出现
		 * [a-zA-Z0-9_]+
		 * 
		 * 2、** .** .** .**组成，
		 * “.**”部分可以表示为
		 * \.[a-zA-Z0-9_-]+
		 * 多个“.**”可以表示为
		 * (\.[a-zA-Z0-9_-]+)+
		 * 
		 * 3、“^”匹配邮箱的开始部分，用“$”匹配邮箱结束部分以保证邮箱前后不能有其他字符
		 * 
		 * 4、最后在"\"之前加上\ 转义字符
		 * 
		 * 
		 * 6、“?”代表匹配前面的字符一个或零个
		 * 
		 * 7、“/”代表一个正则表达式。
		 * 
		 * 8、\d+ 匹配一个或多个数字
		 * 
		 * 9、()? 设置括号内的选项是可选的
		 * 
		 * 10、* 零次或多次匹配前面的字符或子表达式
		 * 
		 * 11、+ 1次或多次匹配前面的字符或子表达式
		 * 
		 * 12、？ 零次或一次匹配前面的字符或子表达式			* > + > ？
		 * 
		 * 12、\d  数字字符匹配。等效于 [0-9]
		 * 
		 * 
		 * 常用的正则表达式
		 * "^-?[1-9]\\d*$"   	匹配整数
		 * [\u4e00-\u9fa5]		汉字的正则表达示
		 * 
		 * 
		 */
		String regex = "^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)+$";
		
		String mail = "falg@163.com";
		
		boolean flag = mail.matches(regex);
		System.out.print(flag);
		
	}

}
