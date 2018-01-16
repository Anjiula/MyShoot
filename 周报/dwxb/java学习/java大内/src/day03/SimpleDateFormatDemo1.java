package day03;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * java.text.simpleDateFormat
 * 
 * 格式化的String 与 date互转
 * simpleDateFormat.format(date)
 * 
 * @author b_anhr
 *
 */
public class SimpleDateFormatDemo1 {

	public static void main(String[] args) {
		//当前系统时间
		Date date = new Date();
		
		System.out.println(date);
		
		/**
		 * 1999-04-27 12:12:12
		 * yyyy-MM-dd HH:mm:ss
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(simpleDateFormat.format(date));
	}

}
