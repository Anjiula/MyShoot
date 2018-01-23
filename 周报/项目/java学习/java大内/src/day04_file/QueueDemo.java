package day04_file;

import java.util.LinkedList;
import java.util.Queue;

/**
 * poll()    offer()    peek()
 * @author b_anhr
 *
 */
public class QueueDemo {

	public static void main(String[] args) {
		
		/**
		 * linkedList实现了队列接口，因为他收尾增删快，符合队列特点
		 */
		Queue<String> queue = new LinkedList<String>();
		
		/**
		 * boolean offer(E e)
		 * 入队操作，向对尾增加一个元素
		 */
		queue.offer("one");
		queue.offer("twi");
		queue.offer("three");
		queue.offer("four");
		
		System.out.println(queue);

		/**
		 * E poll()
		 * 出队操作，从队首取出元素，该元素就从队列中删除 了
		 */
		String string = queue.poll();
		System.out.println(queue);
		
		
		/**
		 * E peek()
		 * 引用队首元素，不做出队操作
		 */
		System.out.println(queue.peek());
		System.out.println(queue);
		
		
		/**
		 * 遍历
		 * 队列的遍历要倒着来  或者用while循环
		 */
//		System.out.println(queue.size());
//		for (int i = queue.size(); i > 0; i--) {
//			String string2 = queue.poll();
//			System.out.println(string2);
//		}
		
		while (queue.size() > 0) {
			String string2 = queue.poll();
			System.out.println(string2);
		}
		
//		for (int i = 0; i < queue.size(); i++) {
//			String string2 = queue.poll();
//			System.out.println(string2);
//		}
		
		System.out.println(queue);
	}

}
