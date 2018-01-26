package chat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 模拟另一个客户端   去连接server
 * 
 * @author b_anhr
 *
 */
public class ClientClone {
	
	private Socket socket;
	
	/**
	 * 初始化clientClone
	 */
//	public ClientClone() {
//		
//		try {
//			System.out.println("client2 :  loading....");
//			socket = new Socket("localhost", 8088);
//			System.out.println("client2 :  success.");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	public ClientClone()throws Exception {
		System.out.println("client:  connact to server....");
		socket = new Socket("localhost", 8088);
		System.out.println("client:  success");
	}
	
	public void start() {
		
		try {
//			OutputStream outputStream = socket.getOutputStream();
//			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
//			PrintWriter printWriter = new PrintWriter(outputStream,true);
//			printWriter.println("1231231123");
			//字节流,通过字节流传输
			OutputStream outputStream = socket.getOutputStream();
			//转换流
			OutputStreamWriter ouWriter = new OutputStreamWriter(outputStream, "UTF-8");
			//字符流(自动刷新true)
			PrintWriter printWriter = new PrintWriter(ouWriter,true);
			
			printWriter.println("服务器");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		try {
			ClientClone clientClone = new ClientClone();
			clientClone.start();
		} catch (Exception e) {
			System.out.println("Client falise");
		}
	}

}
