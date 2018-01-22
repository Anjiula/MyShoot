package day03;

import java.util.Date;

/**
 * java.util.Date
 * 
 * Date类的每一个实例表示一个时间点，即从1970年1月1日00:00:00 开始到表示的时间点之前的毫秒数   用long存储
 * 
 * 1970年以前用负数表示
 * 
 * 由于date设计上存在缺陷（时区，千年虫）所以只用date表示一个时间点。
 * 
 * @author b_anhr
 *
 */
public class DateDemo {

	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date);
		System.out.println(date.getYear());

	}

}
