package plantWar;

import java.util.Random;

public class Bee extends FlyObject implements Award {
	
	//奖励类型
	int awardType;
	
	//蜜蜂的移动速度
	int xSpeed = 1;
	int xAbsoluteSpeed = 1;
	int ySpeed = 1;
	
	Bee(){
		int random = new Random().nextInt(MyShoot.WIDTH);
		this.x = random;
		this.y = -MyShoot.bee.getHeight();
		
		this.image = MyShoot.bee;
	}

	@Override
	public void step() {
		this.x += xSpeed;
		this.y += ySpeed;
		
		if (this.x > MyShoot.WIDTH - MyShoot.bee.getWidth()) {
			xSpeed = (-1) * xAbsoluteSpeed; 
		}
		if (this.x < 0) {
			xSpeed = xAbsoluteSpeed;
		}
		
	}

	@Override
	public boolean outOfBounds() {
		
		return this.getY() > MyShoot.HEIGHT;
	}
	
	/**
	 * 获得奖励
	 */
	public int getType() {
		return awardType;
	}
	
}
