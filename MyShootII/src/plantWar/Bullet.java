package plantWar;

public class Bullet extends FlyObject {

	//移动速度
	static int speed = 6;
	
	
	/**
	 * 构造函数
	 */
	Bullet(int x,int y){
		this.x = x;
		this.y = y;
		
		this.image = MyShoot.bullet1;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		this.y-= speed;
	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return this.getY() < 0;
	}

}
