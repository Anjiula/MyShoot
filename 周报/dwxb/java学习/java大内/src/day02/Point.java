package day02;

public class Point {

	private int x;
	private int y;
	//source->generate....
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Point(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	/**
	 * 自己定义的类要重写tostring方法
	 * 
	 * java自身的类都提供了该重写方法
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + x + "," + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof Point) {
			Point p = (Point)obj;
			return this.x == p.x && this.y == p.y;
		}
		return false;
	}

}
