package plantWar;

public class Hero extends FlyObject {

	//切换索引
	int indexChange = 0;
	//火力值  （默认为0） 单倍
	int fireValue = 10;
	//子弹到下一个子弹产生的时间
	int count = 20;
	//生命值
	int life = 1;
	
	Hero(){
		//默认hero0图片
		this.image = MyShoot.hero0;
		
		//设置初始位置
		this.x = 150;
		this.y = 400;
	}
	
	/**
	 * 射击
	 */
	public Bullet[] shoot() {
		//火力值大于0，发射双列子弹，每发射一次，火力值-1，直到减到0 ，默认发射单列子弹
		int xPoint = MyShoot.hero0.getWidth()/4;
		int yPoint = 10;
		Bullet[] bullets = {};
		if (this.fireValue > 0) {
			bullets = new Bullet[2];
			bullets[0] = new Bullet(xPoint * 1 + this.getX(), this.getY() - yPoint);
			bullets[1] = new Bullet(xPoint * 3 + this.getX(), this.getY() - yPoint);		
			fireValue -= 1;
		}else {
			bullets = new Bullet[1];
			bullets[0] = new Bullet(xPoint * 2 + this.getX(), this.getY() - yPoint);
		}
		return bullets;
	}
	
	/**
	 * 英雄机一定
	 */
	public void moveTo(int x,int y) {
		this.setX(x - MyShoot.hero0.getWidth()/2);
		this.setY(y - MyShoot.hero0.getHeight()/2);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/**
	 * 英雄机增命
	 */
	public void addLife() {
		life++;
	}
	
	/**
	 * 英雄机减少命
	 */
	public void reduceLife() {
		life--;
	}
	
	/**
	 * 获取生命值
	 */
	public int getLife() {
		return this.life;
	}
	
	/**
	 * 英雄机增火力值
	 */
	public void addDoubleFired() {
		fireValue += 40;
	}
	
	/**
	 * 英雄机碰撞
	 */
	public boolean boundHero(FlyObject flyObject) {
		
//		int x1 = flyObject.getX();
//		int x2 = flyObject.getY() + flyObject.getWidth();
//		
//		int y1 = flyObject.getY();
//		int y2 = flyObject.getY() + flyObject.getHeight();
//		
//		return x > x1 && x <x2
//				&&
//				y < y2 && y > y1;
		
		int x1 = flyObject.getX() - this.getWidth()/2;
		int x2 = flyObject.getX() + flyObject.getWidth() + this.getWidth()/2;
		
		int y1 = flyObject.getY() - this.getHeight()/2;
		int y2 = flyObject.getY() + flyObject.getHeight() + this.getHeight()/2;
		
		int x = this.getX() + this.getWidth()/2;
		int y = this.getY() + this.getHeight()/2;
		
		return x > x1 && x < x2
				&&
				y > y1 && y < y2;
	}

}
