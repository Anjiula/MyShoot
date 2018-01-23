package collection;

import java.util.ArrayList;
import java.util.List;

/**
 * arraylist  查询更快，数组实现
 * linkedlist 增删更快，链表实现（收尾增删效果明显）
 * @author b_anhr
 *
 */
public class ListDemo1 {

	public static void main(String[] args) {
		
		/**
		 * E set(int index,E e)  替换元素操作，返回原位置元素
		 * 
		 */
		List<String> list = new ArrayList<String>();
		list.add("123");
		list.add("#");
		list.add("1231");
		list.add("#");
		list.add("12323");
		list.add("#");
		list.add("12");
		
		System.out.println(list);
		
		list.set(1, "2");
		
		System.out.println(list);
		
		/**
		 * remove()  只能删除一个
		 */
		list.remove("#");
		System.out.println(list);
		
		for (String string : list) {
			System.out.println(string);
			if ("#".equals(string)) {
			}
		}
	}

}
