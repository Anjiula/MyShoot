package thread;
/**
 * 使用匿名内部类的方式创建线程
 * 
 * 两种方式:
 * Thread t = new Thread()					(通过继承thread类的方式)
 * Thread t = new Thread(runnabel实例)		(通过实现runnable接口方式)(推荐)
 * 
 * 获取当前线程
 * Thread.currentThread();
 * 
 * 线程相关方法
 * long getId()	返回线程标识符
 * String getName() 返回线程的名称
 * int getPriority() 返回线程优先级
 * Thread.state getState() 获取线程状态
 * boolean isAlive()  是否处于活动状态
 * ...
 * 
 * @author b_anhr
 *
 */
public class ThreadDemo3 {

	public static void main(String[] args) {
		dosome();
		System.out.println("运行main方方法的线程是:" + Thread.currentThread());
		//方式  	(继承Thread类)
		Thread myThread = new Thread() {
			
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println("123123123123");
				}
			}
		};
		
		Thread myThread2 = new Thread() {
			
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println("1");
				}
			}
		};
		
		
		
//		Thread thread = myThread;
//		thread.start();
//		Thread thread2 = myThread2;
//		thread2.start();

		
		//方式二  (实现runnable接口)
		Runnable myRunnable1 = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println("12312312313");
				}
			}
		};
		Runnable myRunnable2 = new Runnable() {
			
			@Override
			public void run() {
				
				for (int i = 0; i < 100; i++) {
					System.out.println("1");
				}
				
			}
		};
		
		Runnable runnable1 = myRunnable1;
		Runnable runnable2 = myRunnable2;
		
		Thread thread3 = new Thread(runnable1);
		Thread thread4 = new Thread(runnable2);
		
//		thread3.start();
//		thread4.start();
		
		//或者简写
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println("12312312313");
					System.out.println(Thread.currentThread());
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println("1");
				}
			}
		}).start();
		
	}
	
	public static void dosome() {
		System.out.println("运行dosome()方法的线程是: (dosome方法在main函数中调用)" + Thread.currentThread());
	}

}
