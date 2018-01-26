package thread;

public class ThreadDemo1 {

	public static void main(String[] args) {

		/**
		 * 第一种创建线程的方式
		 * new 一个线程
		 */
		Thread thread = new MyThread1();
		Thread thread2 = new MyThread2();
		
		/**
		 * 启动线程  进入runnable
		 */
		thread.start();
		thread2.start();
		
		/**
		 * 结论: 两个线程切换
		 * 通过创建继承thread的类来创建线程,不利于线程的重用
		 */
	}

}

class MyThread1 extends Thread{
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("chashuibiao");
		}
	}
}

class MyThread2 extends Thread{
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println("1");
		}
	}
}