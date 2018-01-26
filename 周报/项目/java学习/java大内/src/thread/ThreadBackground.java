package thread;
/**
 * 1、thread1.setDaemon(true) thread1		变成后台线程     而且这个要在启动之前设置
 * 
 * 守护线程 , 又称后台线程,
 * 当一个进程中的所有前台线程都结束时,进程就要结束,
 * 若还有后台线程运行,都会被牵制结束
 * 
 * 2、join()		可以使(调用该方法的线程)()进入阻塞状态,直到(该方法所属线程)(b)完成工作后解除a的阻塞状态
 * 
 * 一个局部内部类中要调用其他局部变量,该变量必须是final
 * 
 * 
 * 
 * @author b_anhr
 *
 */
public class ThreadBackground {

	public static boolean isFinish = false;
	public static void main(String[] args) {
		//表示图片下载状态
		
		final Thread loadThread = new Thread(){
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println("Loading...");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("loadover");
				isFinish = true;
			}
		};
		
		loadThread.start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("开始下载 ");
				/**
				 * 这里要等待download完成,再开始
				 */
				try {
					//这里该线程阻塞,直到loadthread执行完成,继续执行
					loadThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!isFinish) {
					throw new RuntimeException("weixiazaiwancheng");
				}
				
				System.out.println("xiazai wancheng");
				
			}
		}).start();
	}

}
