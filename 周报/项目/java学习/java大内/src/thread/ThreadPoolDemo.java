package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 * 作用:1,重用线程,2,控制线程数量
 * 
 * 线程池中的线程都是前端线程,会缓存,没任务,不会停掉,进程不会停掉         需要手动停掉(shoutdown/shoutdownnow)
 * 
 * threadpool.execute(run实例)

 * 两种结束线程方式
 * 
 * 
 * @author b_anhr
 *
 */
public class ThreadPoolDemo {

	public static void main(String[] args) {

		//创建有2条线程的线程池
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		//新建一个runnable实例
		for (int i = 0; i < 5; i++) {
			Runnable runnable = new Runnable() {
				
				public void run() {
					System.out.println(Thread.currentThread() + ": 正在运行");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread() + ": 运行完毕");
				}
			};
			executorService.execute(runnable);
		}
		
		//结束线程
		executorService.shutdown();
	}

}
