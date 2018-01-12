package day02;
/**
 * JDK1.5后推出新特性 
 * 自动拆装箱
 * @author b_anhr
 *
 */
public class IntegerDemo5 {

	public static void main(String[] args) {
		
		/**
		 *	下面的代码出发了自动拆箱
		 *	在class文件中，下面的代码被编译器修改为
		 *	int i = new Integer(123).intValue();
		 */
		int i = new Integer(123);		//编译器认可自动拆装箱   而不是虚拟机认可 
		
		/**
		 * 下面的代码触发了自动装箱
		 * 在class文件中，下面的代码被编译器修改为
		 * Inerger integer = Integer.valueOf(123);
		 */
		Integer integer = 123;
		
		System.out.println(i + "," + integer);

	}

}
