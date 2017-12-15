package plantWar;

import java.awt.image.BufferedImage;

public abstract class FlyObject {
	
	//飞行物的位置属性
	public int x;
	public int y;
	
	//飞行物的大小属性
	public int width;
	public int height;
	
	//飞行物的图片属性
	public BufferedImage image;
	
	
	//x,y的get、set方法	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	//width、 height 的get、set方法
	public int getWidth() {
		return this.image.getWidth();
	}
	
	public int getHeight() {
		return this.image.getHeight();
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		 this.height = height;
	}
	

	//走一步的方法
	public abstract void step();
	
	//判断是否越界
	public abstract boolean outOfBounds();
	
	//敌人被子弹射击
	public boolean shootBy(Bullet bullet) {
		int x1 = this.x;
		int x2 = this.x + this.getWidth();
		
		int y1 = this.y;
		int y2 = this.y + this.getHeight();
		
		int x = bullet.getX();
		int y = bullet.getY();
		
		//若x在x1x2之间，y在y1,y2之间，则判断为装上了
		return x < x2 && x > x1
				&&
				y < y2 && y >y1;
	}
	

}
