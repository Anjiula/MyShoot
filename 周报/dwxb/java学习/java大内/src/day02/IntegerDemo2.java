package day02;
/**
 * 
 * @author b_anhr
 *
 */
public class IntegerDemo2 {
	public static void main(String[] args) {
		/**
		 * 
		 */
		Integer i = new Integer(123);
		Integer i2 = new Integer(123);
		
		/**
		 * valueOf 方法会重用对象，但只缓存了-128~127 超过就不重用了。（节省内存）
		 */
		Integer i3 = Integer.valueOf(127);
		Integer i4 = Integer.valueOf(127);
		
		
		Integer i5 = Integer.valueOf(128);
		Integer i6 = Integer.valueOf(128);
		
		System.out.println(i == i2);
		System.out.println(i.equals(i2));
		
		/**
		 * 01111111  byte最大值			127
		 * +1
		 * 10000000  超出源码表示范围
		 * 11111111						-1 的补码
		 */
		byte b = i3.byteValue();
		System.out.println(b);
		//intvalue dubleValue  byteValue
		
	}
}
