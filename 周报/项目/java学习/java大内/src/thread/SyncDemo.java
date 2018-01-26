package thread;
/**
 * 互斥锁
 * 
 * synchronized   互斥锁   使用它修饰的代码段,只要锁名相同(带锁的同一对象),代码段就不会被多线程同时执行(具有互斥效果)
 * 
 * @author b_anhr
 *
 */
public class SyncDemo {

	public static void main(String[] args) {
		Adb adb = new Adb(); 
		new Thread(new Runnable() {
			public void run() {
				//线程1调用A方法
				adb.matchA();
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				//线程2调用b方法
				adb.matchB();
			}
		}).start();
		//结论: 调用A方法时给对象上锁,该对象也是调用b方法的对象,所以ab方法也会同步执行(不会被多线程同时执行)
	}
}

class Adb{
	//两个输出方法
	public synchronized void matchA() {
		System.out.println(Thread.currentThread().getName() + ": 正在执行A方法");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ": A方法执行完毕");
	}
	public synchronized void matchB() {
		System.out.println(Thread.currentThread().getName() + ": 正在执行方法");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ": 方法执行完毕");
	}
}
