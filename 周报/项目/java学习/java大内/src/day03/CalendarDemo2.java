package day03;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 1、对时间的操作
 * set(int field)
 * 
 * 2、获取一个calendar表示的日期中，各个时间分量对应的值 
 *  get(int field)
 * 
 * 3、获取某一个时间分量的最大值
 * getActualMaximum(int field)   min
 * 
 * @author b_anhr
 *
 */
public class CalendarDemo2 {

	public static void main(String[] args) {
		
		List<String> monthList = new ArrayList<String>();
		monthList.add("1月");
		monthList.add("2月");
		monthList.add("3月");
		monthList.add("4月");
		monthList.add("5月");
		monthList.add("6月");
		monthList.add("7月");
		monthList.add("8月");
		monthList.add("9月");
		monthList.add("10月");
		monthList.add("11月");
		monthList.add("12月");
		
		
		List<String> weekList = new ArrayList<String>();
		weekList.add("周日");
		weekList.add("周一");
		weekList.add("周二");
		weekList.add("周三");
		weekList.add("周四");
		weekList.add("周五");
		weekList.add("周六");
		
		//1、实例化
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar);
		System.out.println(calendar.getTime());
		
		
		/**
		 * 单独设置calendar的时间分量     其他也会随着改变
		 * 设置年
		 */
		calendar.set(Calendar.YEAR, 2008);
		System.out.println(calendar);
		System.out.println(calendar.getTime());
		
		
		
		//2、实例化
		Calendar calendar2 = calendar.getInstance();
		/**
		 * get(field)  方法获取各时间分量对应的值
		 */
		calendar2.get(Calendar.YEAR);
		/**
		 * 1月是0
		 * 周日是1  周六是7
		 */
		System.out.println(calendar2.get(Calendar.YEAR) + "年 -" + monthList.get(calendar2.get(calendar.MONTH)) + "-" + calendar2.get(Calendar.DAY_OF_MONTH) + "日 -"
		 + weekList.get(calendar2.get(Calendar.DAY_OF_WEEK) - 1) + "- 该月的第" + calendar2.get(Calendar.DAY_OF_WEEK_IN_MONTH) + "周");
		
		
		System.out.println("house(12小时制) " + calendar2.get(Calendar.HOUR) + "---" + "house_of_day(24小时制) " + calendar2.get(Calendar.HOUR_OF_DAY));
		
		System.out.println("迄今为止，" + calendar2.get(Calendar.YEAR) + "已经过了多少天： " + calendar2.get(Calendar.DAY_OF_YEAR));
		
		
		//3、实例化
		Calendar calendar3 = Calendar.getInstance();
		
		System.out.println("本月 " + monthList.get(calendar3.get(Calendar.MONTH)) + "一共" + calendar3.getActualMaximum(Calendar.DAY_OF_MONTH) + "天： ");
		
		System.out.println("今年" + calendar3.get(Calendar.YEAR) + "一共" + calendar.getActualMaximum(Calendar.DAY_OF_YEAR) + "天");
		
	}

}
