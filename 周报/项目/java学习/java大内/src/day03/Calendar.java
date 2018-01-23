package day03;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * java.util.Calendar(抽象类)
 * 对时间进行操作  
 * 
 * 日历类 抽象类 封装了对日期的相关操作 比如计算时间
 * 
 * 
 * calendar  ->  date				calendar.getTime()
 * date  ->   calendar				calendar.setTime(date)
 * 
 * @author b_anhr
 *
 */
public class Calendar {

	public static void main(String[] args) {
		
		/**
		 * getInstance 该方法会根据当前系统所在地区创建一个相应的实现，
		 * 通常是GregorianCalendar
		 * 默认创建的日历实例表示当前系统时间
		 */
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		/**
		 * tostring方法不能直观反映表示的日期
		 */
		System.out.println(calendar);
		
		/**
		 * calendar  ->  date
		 * 
		 */
		Date date = calendar.getTime();
		
		System.out.println(date);
		
		/**
		 * date  ->   格式化date
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String string = sdf.format(date);

		System.out.println(string);
		
		/**
		 * date  ->   calendar
		 */
		calendar.setTime(new Date());
		
		
		
	}

}
