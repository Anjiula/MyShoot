package day04_file;

public class Point implements Comparable<Point> {
	
	private int x;
	private int y;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
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
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * 定义规则
	 * 
	 * @return 	>0	当前对象大
	 * 			<0	参数大
	 * 			=0	二者相等
	 */
	public int compareTo(Point o) {
		
		int len = this.x * this.x + this.y * this.y;
		int leno = o.x * o.x + o.y * o.y;
		
//		if (len < leno) {
//			return 1;
//		}else if (len < leno) {
//			return -1;
//		}else{
//			return 0;
//		}
		return len - leno;
	}

	//系统自动添加hashCode()equals()方法
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
