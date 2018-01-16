package day03;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将String按指定日期格式转换为一个date对象
 * sdf.parse（string）
 * 
 * @author b_anhr
 *
 */
public class SimpleDateFormatDemo {

	public static void main(String[] args) {
		
		String string = "1999-04-27 12:12:12";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date date = sdf.parse(string);
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
