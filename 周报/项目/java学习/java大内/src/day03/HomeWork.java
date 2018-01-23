package day03;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * 计算有效日期
 * 输入一个生产日期，一个保质期的天数
 * 促销日为该商品的国企日前两周的收伞
 * 
 * 
 * calendar计算时间差   			Calendar.getTimeInMillis() 
 * 
 * 得到毫秒差换算成其他
 * 
 * @author b_anhr
 *
 */
public class HomeWork {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入生产日期（yyyy-MM-dd）：");
		String productionDateString = scanner.next();
		
		Scanner scanner2 = new Scanner(System.in);
		System.out.println("请输入保质期天数：");
		int shelfTime = Integer.parseInt(scanner.next());
		
		//sring -> Date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date productionDate = new Date();
		try {
			productionDate = sdf.parse(productionDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//Date -> calendar
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(productionDate);
		
		
		//calendar计算过期日期
		calendar.add(Calendar.DAY_OF_YEAR, shelfTime);
		
		
		
		//calendar  ->  date
		Date date = calendar.getTime();
		
		//date -> string  过期日期
		String expiredDateString = sdf.format(date);
		
		System.out.println("过期日期为：" + expiredDateString);
		
		//计算促销日期
		calendar.add(Calendar.WEEK_OF_YEAR, -2);
		
		//calendar  ->  date
		Date date2 = calendar.getTime();
		
		//date -> string  过期日期
		String saleDateString = sdf.format(date2);
		
		System.out.println("促销日期为：" + saleDateString + "\n那天是：" + CalendarDemo2.weekList.get(6));
	}

}
