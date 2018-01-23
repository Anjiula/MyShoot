package collection;
/**
 * 伪泛型  原型就是obj
 * 
 * 定义了泛型只是编译器在做一些验证工作
 * 
 * 泛型设置值时，编译器会检查是否满足类型要求
 * 
 * 取泛型值时，编译器会自动进行类型转换
 * 
 * 
 * 详细例子减foreachDemo
 * 
 * @author b_anhr
 *
 * @param <T>
 */
public class Point<T> {
	
	private T x;
	private T y;
	
	public Point(T x, T y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public T getX() {
		return x;
	}
	public void setX(T x) {
		this.x = x;
	}
	public T getY() {
		return y;
	}
	public void setY(T y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
