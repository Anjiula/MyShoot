package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 取子集
 * list subList(int start,int end)
 * 
 * 获取当前集合中指定范围的子集，含头不含尾
 * 
 * 对子集（sublist）的修改，就是修改原集合相应的内容
 * 
 * @author b_anhr
 *
 */
public class ListDemo2Sublist {

	public static void main(String[] args) {

		List<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		System.out.println(list);
		
		//截取list 含头不含尾(下标)
		List<Integer>list2 = list.subList(3, 8);
		
		System.out.println(list2);
		
		//子集中所有元素X10 
		
		for (int i = 0; i < list2.size(); i++) {
			list2.set(i, list2.get(i) * 10);
		}
		/**
		 * 对子集（sublist）的修改，就是修改原集合相应的内容
		 */
		System.out.println(list);
		
		/**
		 * 删除集合的2-8的元素
		 */
		list.subList(2, 9).clear();
		System.out.println(list);
	}

}
