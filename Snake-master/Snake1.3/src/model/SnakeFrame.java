package model;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * ��ɵĹ��ܣ�����һ���������߳����ػ�����
 * */

public class SnakeFrame extends Frame{
	//����Ŀ�Ⱥͳ���
	public static final int BLOCK_WIDTH = 15 ;
	public static final int BLOCK_HEIGHT = 15 ;
	//����ķ��������������
	public static final int ROW = 40;
	public static final int COL = 40;
	public static void main(String[] args) {
		new SnakeFrame().launch();
	}
	
	public void launch(){
		
		this.setTitle("Snake");
		this.setSize(ROW*BLOCK_HEIGHT, COL*BLOCK_WIDTH);
		this.setLocation(300, 400);
		this.setBackground(Color.WHITE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		this.setResizable(false);
		this.setVisible(true);
		
		new Thread(new MyPaintThread()).start();
	}

	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		/*
		 * �����滭����ROW*COL�ķ��񹹳�,����forѭ�����ɽ��
		 * */
		for(int i = 0;i<ROW;i++){
			g.drawLine(0, i*BLOCK_HEIGHT, COL*BLOCK_WIDTH,i*BLOCK_HEIGHT );
		}
		for(int i=0;i<COL;i++){
			g.drawLine(i*BLOCK_WIDTH, 0 , i*BLOCK_WIDTH ,ROW*BLOCK_HEIGHT);
		}
		
		g.setColor(c);
	}
	
	/*
	 * �ػ��߳���
	 * */
	private class MyPaintThread implements Runnable{

		@Override
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
