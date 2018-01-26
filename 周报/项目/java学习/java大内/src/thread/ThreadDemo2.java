package thread;
/**
 * 第二种创建线程的方式
 * 实现runabele接口,重写run方法
 * @author b_anhr
 *
 */
public class ThreadDemo2 {

	public static void main(String[] args) {
		
		//单独定义任务
		Runnable r1 = new MyRunnable1();
		Runnable r2 = new MyRunnable2();

		//创建线程
		//使任务和线程之间较少耦合,并且可以继承其他类
		Thread thread = new Thread(r1);
		Thread thread2 = new Thread(r2);
		
		//开启线程
		thread.start();
		thread2.start();
		
		//推荐使用
		//线程池使用该方式
	}

}

class MyRunnable1 implements Runnable{
	@Override
	public void run() {
		
		System.err.println("123123123");
		
	}
}
class MyRunnable2 implements Runnable{
	@Override
	public void run() {
		
		System.err.println("1");
		
	}
}
