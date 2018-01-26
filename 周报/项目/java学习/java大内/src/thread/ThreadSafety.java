package thread;


/**
 * Stringbuffer    Stringbuilder(线程不安全)    
 * 
 * 多个线程访问同一段资源,会出现线程安全  =>  线程锁
 * 
 * 一旦把一个异常抛到某线程run方法之外,该线程死掉(要避免,即自己trycatch)
 * 
 * @author b_anhr
 *
 */
public class ThreadSafety {

	public static void main(String[] args) {
		Tables t = new Tables();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (true) {
					int bean = t.getBean();
					//模拟CPU到这里结束
					Thread.yield();
					System.out.println(Thread.currentThread().getName() + "rest" +  bean);
				}
				
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (true) {
					int bean = t.getBean();
					//模拟CPU到这里结束
					Thread.yield();
					System.out.println(Thread.currentThread().getName() + "rest" +  bean);
				}
				
			}
		}).start();
		
		
		//继承Thread(重写run方法)方法创建线程
//		Tables t = new Tables();
//		Thread thread = new Thread(){
//			@Override
//			public void run() {
//				while (true) {
//					int beans = t.getBean();
//					System.out.println(getName() + beans);
//				}
//			}
//		};
//		
//		thread.start();

	}
}

class Tables {
	private int beans = 20;
	//synchronized 给方法上锁,即该方法同一时间不会有多个线程访问
	public synchronized int getBean() {
		if (beans == 0) {
			throw new RuntimeException("meiyoudoauzi");
		}
		//模拟CPU到这里结束		模拟线程没时间返回.就切换到另一个线程	
		Thread.yield();
		return beans--;
	}
}
