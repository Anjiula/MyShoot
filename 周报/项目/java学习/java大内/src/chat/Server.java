package chat;
/**
 * serverSocket  负责
 * 1, 向系统请求服务端口,是客户端链接
 * 2, 监听申请的端口,当客户端通过该端口连接时,serverSocket会在服务端创建一个socket与客户端建立链接.(每一个客户端链接就在服务端创建一个socket对应)
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import sun.applet.Main;

/**
 * @author b_anhr
 *
 */
public class Server {
	
	ServerSocket serverSocket = new ServerSocket();
	
	public Server()throws Exception {
		serverSocket = new ServerSocket(8088);
	}
	
	public void start() {
		try {
			while (true) {
				//等待多个client连接
				System.out.println("server:  load Client connect..."); 
				Socket socketSer = serverSocket.accept();
				System.out.println("server:  client connect success");
				
				//连接后创建一个线程    将监听得到的socket传入线程去执行操作
				Runnable runnableIntance = new ClientHandler(socketSer);
				Thread thread = new Thread(runnableIntance);
				thread.start();
				
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("server failse");
		}
	}
	

}

class ClientHandler implements Runnable{
	/**
	 * 处理客户端的socket
	 */
	Socket socket = new Socket();
	
	public ClientHandler(Socket s) {
		this.socket = s;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				//等待client发送数据
				InputStream inputStream = this.socket.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String string= bufferedReader.readLine();
				System.out.println("server: clinet say : " + string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}