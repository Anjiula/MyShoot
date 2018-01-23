package day03;

import java.util.Calendar;

/**
 * calendar中计算时间的方法
 * void add(int field ,int value) 
 * 对给定的时间分量加上给定的值，若给定的值是负数，就是减
 * 
 * @author b_anhr
 *
 */
public class CalendarDemo3 {

	public static void main(String[] args) {
		
		//查看3年2个月1天后的日期
		
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime());
		
		
		calendar.add(Calendar.YEAR, 3);
		System.out.println(calendar.getTime());
		
		//DAY_OF_YEAR   可能跨年
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		System.out.println(calendar.getTime());
		
		//遇到开发需求时         (string)用户输入->(simpleDatef) ->date-> calendar ->  (计算) -> calendar -> date -> simpleDatef -> 格式化输出(string)

		
	}

}
