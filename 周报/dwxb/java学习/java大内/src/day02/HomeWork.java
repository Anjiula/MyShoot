package day02;

/**
 * 作业一： 
 * 判断输入的数字是小数还是整数
 * 若是小数转换乘以5后输出		（有保留2位小数  ,两种方法）
 * 若是整数转换乘以10后输出
 * 
 * 
 * 
 * 
 * 
 * 小结： 保留2位小数
 * DecimalFormat df = new DecimalFormat("#.00");
 * df.format(f)
 * 
 * String.format("%.2f", f)
 * 
 * 返回的都是字符串
 */
import java.text.DecimalFormat;

import com.sun.xml.internal.bind.v2.model.core.Adapter;

public class HomeWork {

	public static void main(String[] args) {
		
		//write a string of number and decimal point only
		String string = "10363.54";
		
		/**
		 * judging the regularity of the decimal
		 */
		String regex = "^[-]?[1-9]\\d*\\.\\d*|[-][0-9]?\\d*\\.\\d*$";
		
		/**
		 * judging the regularity of the integer
		 */
		String regex2 = "^-?[1-9]\\d*$";
		
		if (HomeWork.matchString(string, regex)) {
			//decimal
			double d = Double.parseDouble(string);
			//方法一   
			System.out.println("this number is decimals : " + d + "\n" + "five multiple result : " + HomeWork.transFormToDouble(d * 5));
			System.out.println();
			//方法二   
			System.out.println("this number is decimals : " + d + "\n" + "five multiple result : " + String.format("%.2f", d * 5));
		}else if (HomeWork.matchString(string, regex2)) {
			//integer
			int i = Integer.parseInt(string);
			System.out.println("this number is integer : " + i + "\n" + "ten multiple result : " + i * 10);
		}else {
			System.out.println("that is not a number");
		}
		
	}
	
	/**
	 *  
	 * @param string	string to be judged
	 * @param regex		regular
	 * @return			0no    1yes
	 */
	public static boolean matchString(String string, String regex) {
		if (string.matches(regex)) {
			return true;
		}
		return false;
	}
	
	/**
	 * keep two bits after decimal
	 * double to double
	 * use decimalFmormat
	 * @return
	 */
	public static double transFormToDouble(double num) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");					
		return Double.parseDouble(decimalFormat.format(num));
	}
}




