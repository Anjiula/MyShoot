package day04_file;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 存储一组元素，先进后出
 * 通常实现后退这一功能 
 * 
 * 双端队列（只用于一端进出操作时）  == 栈结构			push()   pop()   poll()    offer()
 * @author b_anhr
 *
 */
public class DequeDemo {

	public static void main(String[] args) {
		
		Deque<String> stack = new LinkedList<String>();
		
		stack.push("one");
		stack.push("two");
		stack.push("three");
		stack.push("four");

		System.out.println(stack);
		
		System.out.println(stack.pop());
		
		/**
		 * 遍历~  一种特殊的队列
		 */
		while (stack.size() > 0) {
			String string = stack.pop();
			System.out.println(string);
		}
	}

}
