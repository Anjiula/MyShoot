package plantWar;

import java.util.Random;

public class EnemyPlane extends FlyObject implements Enemy {
	
	//敌机的移动速度
	int speed = 1;
	
	EnemyPlane(){
		int random = new Random().nextInt(MyShoot.WIDTH);
		this.x = random;
		this.y = -this.height;
		
		this.image = MyShoot.airplane;
	}

	@Override
	public void step() {
		this.y += speed;
		
	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return this.getY() > MyShoot.HEIGHT;
	}
	
	public int getScore() {
		return 5;
	}

}
