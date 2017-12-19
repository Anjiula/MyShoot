package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

	public class Snake {
		//λ��
		private int x;
		private int y;
		//��С
		private static final int WIDTH = 10 ;
		private static final int HEIGHT = 10 ;
		
		//����
		private Direction dir =Direction.D;
		
		private SnakeFrame snakeFrame ;
		
		public Snake(int x, int y, SnakeFrame snakeFrame) {
			this.x = x;
			this.y = y;
			this.snakeFrame = snakeFrame;
		}
		
		public Snake(int x, int y,Direction dir, SnakeFrame snakeFrame) {
			this(x,y,snakeFrame);
			this.dir = dir ;
		}
	
		public void draw(Graphics g){
			
			Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(x, y, WIDTH, HEIGHT);
			g.setColor(c);
			
			move();
		}
	
		/*
		 * �������ܣ����ݰ�����ȷ���ߵ��ƶ�����
		 * */
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			switch(key){
			case KeyEvent.VK_LEFT :
				this.dir = Direction.L;
				break;
			case KeyEvent.VK_UP :
				this.dir = Direction.U;
				break;
			case KeyEvent.VK_RIGHT :
				this.dir = Direction.R;
				break;
			case KeyEvent.VK_DOWN :
				this.dir = Direction.D;
				break;
			default:
				this.dir = Direction.STOP;	
				
			}
			
	
		}
		
		/*
		 * ���ݷ���ľ����ƶ�
		 * */
		public void move(){
			
			if(dir == Direction.L){
				x = x - WIDTH;
			}
			else if(dir == Direction.U){
				y = y - HEIGHT;
			}
			else if(dir == Direction.R){
				x = x + WIDTH;
			}
			else if(dir == Direction.D){
				y = y + HEIGHT;
			}
			
			//�����ߵı߽�
			if(x<=0){
				x = 0;
			}
			else if(x>SnakeFrame.GAME_WIDTH - WIDTH){
				x = SnakeFrame.GAME_WIDTH - WIDTH;
			}
			
			if(y<=0){
				y = 0;
			}
			else if(y>SnakeFrame.GAME_HEIGHT - HEIGHT){
				y = SnakeFrame.GAME_HEIGHT - HEIGHT ;
			}
			
		}
		
		
	}
