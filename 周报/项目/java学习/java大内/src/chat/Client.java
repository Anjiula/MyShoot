package chat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * (套接字)
 * java.net.Socket
 * 
 * 封装了tcp协议, socket是运行在客户端的
 * @author b_anhr
 *
 */
public class Client {

	private Socket socket;
	
	
	//初始化过程中有异常就抛出去
	/**
	 * 构造方法;初始化客户端
	 * @throws Exception
	 */
	public Client()throws Exception {
		System.out.println("client:  connact to server....");
		socket = new Socket("localhost", 8088);
		System.out.println("client:  success");
	}
	
	/**
	 * 启动客户端
	 */
	public void start() {
		try {
			//字节流,通过字节流传输
			OutputStream outputStream = socket.getOutputStream();
			//转换流
			OutputStreamWriter ouWriter = new OutputStreamWriter(outputStream, "UTF-8");
			//字符流(自动刷新true)
			PrintWriter printWriter = new PrintWriter(ouWriter,true);
			
//			printWriter.println("你好,服务器");
			
			
			while (true) {
				Scanner scanner = new Scanner(System.in);
				String string = scanner.nextLine();
				printWriter.println(string);
			}
			
			//flash()
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Client client = new Client();
			client.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("客户端启动失败");
		}
	}
}
