package plantWar;

import java.awt.Color;
import java.awt.Desktop.Action;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author b_anhr
 *
 */
public class MyShoot extends JPanel {
	
	//test_初始化值
	//实例化该对象时，会想走构造函数，若没有，就使用初始值。
	//private String name = "name1";
	
	//定义共有属性
	public Hero hero = new Hero();
	//敌机  + 蜜蜂
//	private FlyObject[] flyings = new FlyObject[2];
	private FlyObject[] flyings = {};
	//子弹
	private Bullet[] bullets = {};
//	private Bullet[] bullets2 = new Bullet[2];
//	private Bullet[] bullets3 = new Bullet[0];
//	private Bullet[] bullets4;
	
	//定义图片属性
	public static BufferedImage airplane;
	public static BufferedImage background;
	public static BufferedImage bee;
	public static BufferedImage bullet1;
	public static BufferedImage gameover;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage start;
	
	//定义四种状态
	public static final int STSRT = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	
	private int state = STSRT;				//默认启动状态
	
	
	//定义面板属性
	public static int WIDTH = 400;
	public static int HEIGHT = 600;
	
	//页面刷新时间
	public static int value = 20;
	
	static{
		//初始化图片属性
		
		try {
			airplane = ImageIO.read(MyShoot.class.getResource("airplane.png"));
			background = ImageIO.read(MyShoot.class.getResource("background.png"));
			bee = ImageIO.read(MyShoot.class.getResource("bee.png"));
			bullet1 = ImageIO.read(MyShoot.class.getResource("bullet1.png"));
			gameover = ImageIO.read(MyShoot.class.getResource("gameover.png"));
			hero0 = ImageIO.read(MyShoot.class.getResource("hero0.png"));
			hero1 = ImageIO.read(MyShoot.class.getResource("hero1.png"));
			pause = ImageIO.read(MyShoot.class.getResource("pause.png"));
			start = ImageIO.read(MyShoot.class.getResource("start.png"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 画子弹
	 */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			g.drawImage(bullets[i].image,bullets[i].getX(),bullets[i].getY(),null);
		}
	}
	
	public void paintAirplane(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			g.drawImage(flyings[i].image,flyings[i].getX(),flyings[i].getY(),null);
		}
	}
	
	/* 
	 * 画分数
	 */
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(0xfff000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));			 //宋书  ，加粗，24号
		g.drawString("SCORE: " +score,10,25);
		g.drawString("LIFE " + hero.life, 10,50);
	}
	
	/* 
	 * 画状态
	 */
	public void paintState(Graphics g) {
		switch (this.state) {
		case STSRT:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	
	/* 
	 * 重写paint方法
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		//画背景
		g.drawImage(background,0,0,null);
		//画英雄机
		g.drawImage(hero0,this.hero.getX(),this.hero.getY(),null);
		//画子弹
		paintBullets(g);
		//画飞行物
		paintAirplane(g);
		//画分数
		paintScoreAndLife(g);
		//画状态
		paintState(g);
	}
	
	/**
	 * 移动一步
	 */
	public void step() {
		//间隔20ms走一次
		//子弹移动一步
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}
		
		//飞行物移动一步
		for (int i = 0; i < flyings.length; i++) {
			flyings[i].step();
		}
	}
	
	/**
	 * 子弹发射
	 */
	int bulletIndex = 0;			//设置时间间隔
	public void shootAction() {
		bulletIndex++;
		if (bulletIndex%20 == 0) {
			//20 * 20 ms 走一次
			Bullet[] bulletsTem = this.hero.shoot();
//			Bullet bullet = new Bullet(hero.getX(), hero.getY());
//			//产生一个子弹       	放到原来数组最后
//			//数组扩容
			bullets = Arrays.copyOf(bullets, bullets.length + bulletsTem.length);
			System.arraycopy(bulletsTem, 0, bullets, bullets.length - bulletsTem.length, bulletsTem.length);
		}
	}
	
	public FlyObject nextFlyObject() {				//40 * 20ms  走一次
		//随机生成0~10 的随机数，如果为0生成蜜蜂，否则为敌机
		int x = new Random().nextInt(10);
		if (x == 0) {
			return new Bee();
		}else {
			return new EnemyPlane();
		}
	}
	
	
	/**
	 * 飞行物入场
	 */
	int flyIndex = 0;			//设置产生飞行物的时间间隔
	public void enterFlyingAndBullet() {
		flyIndex++;
		if (flyIndex%40 == 0) {				//40 * 20ms  走一次
			//随机生成一个飞行物，并添加到飞行物数组最后
			FlyObject flyObject = nextFlyObject();
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = flyObject;
		}
	}
	
	/**
	 * 删除越界元素
	 */
	public void deleteOutOfBounds() {
		//遍历所有子弹，不越界的子弹加入新的子弹数组，便利完成后再复制给原数组
		Bullet[] bulletsTem = new Bullet[bullets.length];
		int index = 0;
		for (int i = 0; i < bullets.length; i++) {
			if (!bullets[i].outOfBounds()) {
				bulletsTem[index] = bullets[i];
				index++;
			}
		}
		//注释代码有问题，会崩溃
		//System.arraycopy(bulletsTem, 0, bullets, 0, index);
		bullets = Arrays.copyOf(bulletsTem, index);
		//遍历所有飞行物，
		FlyObject[] flyObjectsTem = new FlyObject[flyings.length];
		int objIndex = 0;
		for (int i = 0; i < flyings.length; i++) {
			if (!flyings[i].outOfBounds()) {
				flyObjectsTem[objIndex] = flyings[i];
				objIndex++;
			}
		}
	 	//System.arraycopy(flyObjectsTem, 0, flyings, 0, objIndex);
		flyings = Arrays.copyOf(flyObjectsTem, objIndex);
	}
	
	/**
	 * 删除被撞的敌人
	 */
	public void deleteBreakFly(FlyObject fly) {
		flyings[flyings.length - 1] = fly;
		
		flyings = Arrays.copyOf(flyings, flyings.length - 1);
	}
	
	/**
	 * 删除被撞击的飞行物
	 * @param flyObject    		被撞飞行物对象
	 * @param index				被撞飞行物下标
	 */
	public void deleteFlyObj(FlyObject flyObject,int index) {
		FlyObject f = flyings[flyings.length - 1];
		flyings[flyings.length - 1] = flyObject;
		flyings[index] = f;
		
		flyings = Arrays.copyOf(flyings, flyings.length - 1);
	}
	
	/**
	 * 更具奖励实例，实现对英雄机的不同奖励
	 * @param award  			奖励实例
	 */
	public void putAward(Award award) {
		int type = award.getType();
		switch (type) {		//根据奖励类型
		case Award.LIFE:
			hero.addLife();
			break;
		case Award.DOUBLE_FIRE:
			hero.addDoubleFired();
			break;
		}
	}
	
	/**
	 * 一个子弹于飞行物碰撞测试
	 */
	public void boundBullet(Bullet bu) {
		int index = -1;				//存储被撞敌人的下标
		for (int i = 0; i < flyings.length; i++) {
			FlyObject f = flyings[i];
			if (f.shootBy(bu)) {
				//撞上了
				index = i;
				break;
			}
		}
		
		if (index != -1) {
			//根据下标获取被撞的对象
			FlyObject flyObject = flyings[index];
			if (flyObject instanceof Enemy) {//判断是实现敌人接口的实例
				Enemy enemy = (Enemy)flyObject;
				score += enemy.getScore();
			}
			if (flyObject instanceof Award) {//判断是实现奖励接口的实例
				
				//根据奖励type执行不同的奖励方式
				Award award = (Award)flyObject;
				putAward(award);
			}
			//删除被撞的敌人
			//将flyings中的被撞的删除
			deleteFlyObj(flyObject,index);
		}
	}
	
	int score = 0;
	/**
	 * 碰撞
	 */
	public void boundAction() {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			//一个子弹于所有飞行物碰撞测试
			boundBullet(b);
			
		}
	}
	
	/**
	 * 英雄机碰撞
	 */
	public void checkGameOverAction() {
//		for (int i = 0; i < flyings.length; i++) {
//			FlyObject f = flyings[i];
//			
//			if (hero.boundHero(f)) {					//判断撞上
//				if (f instanceof Award) {				//如果是实现奖励接口的实例
//					//生命值 + 火力值增加
//					Award award = (Award)f;				//飞行物对象强转为接口类
//					putAward(award);
//				}
//				if (f instanceof EnemyPlane) {
//					hero.reduceLife();
//					if (hero.life == 0) {
//						//游戏结束
//					}
//				}
//				//蜜蜂消失
//				deleteFlyObj(f, i);
//				break;
//			}
//		}
		
		if (isGameOver()) {
			//为true 游戏结束
			this.state = GAME_OVER;
		}
	}
	
	/**
	 * 判断是否游戏结束            为true游戏结束
	 */
	public boolean isGameOver() {
		for (int i = 0; i < flyings.length; i++) {
			FlyObject f = flyings[i];
			
			if (hero.boundHero(f)) {
				hero.reduceLife();
				//减少火力值
				
				deleteFlyObj(f, i);
			}
		}
		
		
		return this.hero.life <= 0;
	}
	
	/**
	 * 开始游戏
	 */
	public void action() {
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			
			//重写的名字不能变
			
			//重写鼠标移动事件
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseMoved(e);
				if (state == RUNNING) {

					hero.moveTo(e.getX(), e.getY());
				}
			}
			
			//重写鼠标点击事件
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case STSRT:
					state = RUNNING;
					break;

				case GAME_OVER:
					//所有数据归0
					score = 0;
					hero = new Hero();
					flyings = new FlyObject[0];
					bullets = new Bullet[0];
					
					state = STSRT;
					break;
				}
			}
			
			//重写鼠标移出事件
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}
			
			//重写鼠标移入事件
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
				}
			}
		};
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		
		//设置时间						
		
		//设置定时器
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				//间隔20ms走一次
				if (state == RUNNING) {				//运行状态下
					//飞行物 + 子弹入场
					enterFlyingAndBullet();
					//对象移动一步
					step();
					//子弹发射
					shootAction();
					//删除越界元素
					deleteOutOfBounds();
					//子弹碰撞
					boundAction();
					//英雄机碰撞
					checkGameOverAction();
				}
				
				//重绘
				repaint(); 
			}
		}, value, value);									//10ms  
	}	

	public static void main(String[] args) {
		//画面版
		JFrame frame = new JFrame();
		
		MyShoot myShoot = new MyShoot();
		frame.add(myShoot);
		//设置总在最前
		frame.setAlwaysOnTop(true);
		//设置大小
		frame.setSize(WIDTH, HEIGHT);
		//设置相对位置
		frame.setLocationRelativeTo(null);
		//关闭时关闭服务
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//快速可视
		frame.setVisible(true);
		
		//开始游戏
		myShoot.action();
		
	}
}