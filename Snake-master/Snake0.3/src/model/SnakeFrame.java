package model;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/*
 * ��ɹ��ܣ�������һ���߳��������ػ���
 * �����Snake�࣬����Ӽ��̵ķ�����������ߵ��˶�
 * */
public class SnakeFrame  extends Frame{

	
	//��Ϸ����Ĵ�С
	public static final int GAME_WIDTH =400;
	public static final int GAME_HEIGHT =400;
	//��Ϸ�з���Ĵ�С
	private static final int squareSize = 10;
	
	Snake s = new Snake(40,40,Direction.D,this);
	
	public static void main(String[] args) {
		
		SnakeFrame s = new SnakeFrame();
		s.launchFrame();

	}

	private void launchFrame() {
		
		this.setLocation(300, 400);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.WHITE);
		this.setTitle("Snake");
		//��ӹرյĴ����¼�
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		//��ֹ�ı䴰�Ĵ�С
		this.setResizable(false);
		this.setVisible(true);
		
		new Thread(new MyPaintThread()).start();
		
		this.addKeyListener(new MyKeyListener());
		
	}
	
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		
		/*
		 * ����
		 * */
		int col = GAME_WIDTH/squareSize;
		int row = GAME_HEIGHT/squareSize;
		for(int i=0;i<col;i++){
			g.drawLine(i*squareSize, 0, i*squareSize, GAME_HEIGHT);
		}
		for(int i=0;i<row;i++){
			g.drawLine(0, i*squareSize, GAME_WIDTH, i*squareSize);
		}
		g.setColor(c);
		
		s.draw(g);
	}
	
	/*
	 * �ػ��߳���
	 * */
	private class MyPaintThread implements Runnable{

		@Override
		public void run() {
			//ÿ��50ms�ػ�һ��
			while(true){
				repaint();//���Զ�����paint����
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	
	/*
	 * ���̼�����
	 * */
	private class MyKeyListener extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			s.keyPressed(e);
		}
		
	}
	
}
