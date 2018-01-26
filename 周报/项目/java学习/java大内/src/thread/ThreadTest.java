package thread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时钟,每隔一秒输出当前事假
 * 用sleep每隔一秒输出系统当前时间
 * @author b_anhr
 *
 */
public class ThreadTest {

	public static void main(String[] args) {

		while (true) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,hh:mm-ss");
			System.out.println(sdf.format(new Date()));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
