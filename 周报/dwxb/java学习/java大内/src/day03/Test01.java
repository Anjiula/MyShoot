package day03;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 要求用户输入自己的生日，
 * 经过计算 
 * 算出到今为止活了多少天
 * 
 * 以及距离10000天还要多少天
 * 
 * 以及10000天的纪念日是：
 * 
 * 
 * 
 * 小结：
 * string按指定日期格式转化为date				sdf.parse(string)
 * long型的毫秒数转化为date					new date(long ms)
 * 
 * date转为long型的毫秒数						date.getTime()
 * date转化为指定日期格式String				sdf.format(date)
 * 
 * @author b_anhr
 *
 */
public class Test01 {

	public static void main(String[] args) {
		
		System.out.println("输入你的生日");
		Scanner scanner = new Scanner(System.in);
		
		String birString = scanner.nextLine();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//nowdate
		Date now = new Date();
		
		//put birString to date
		try {
			//birthday
			Date birthDay = sdf.parse(birString);
			long duringMs = (now.getTime() - birthDay.getTime());
			long duringDays = duringMs/1000/60/60/24;
			System.out.println("you have been living for " + duringDays + " days");
			
			long restDays = 10000 - duringDays;
			System.out.println("It is " + restDays + " days away form 10000 days.");
			
			//毫秒转日期
//			Date tempDate = new Date(birthDay.getTime());
//			System.out.println(sdf.format(tempDate));
			
			//miliscend  transfrom into date
//			long totalMiliscend = birthDay.getTime();
//			long millions = 10000 * 24 * 60 * 60 *1000;
			
			long totalMiliscend = now.getTime() + restDays * 24 *60*60*1000;
			
			Date totalDate = new Date(totalMiliscend);
			System.out.println("you ten thousand days anniversary is "+ sdf.format(totalDate));
			
			
			//毫秒转日期时，ms最好用long型，不然int不够，会溢出
//			long tempMilisecond = 30;
//			int tempMilisecond2 = 30;
//			
//			System.out.println(sdf.format(new Date(tempMilisecond * 24 *60*60*1000)));
//			System.out.println(sdf.format(new Date(tempMilisecond2 * 24 *60*60*1000)));
//			
//			System.out.println(tempMilisecond * 24 *60*60*1000);
//			System.out.println(tempMilisecond2 * 24 *60*60*1000);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
