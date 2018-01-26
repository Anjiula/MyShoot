package thread;
/**
 * haset  本质上就是   hashmap
 * 
 * 就算是线程安全的集合,其中对于元素的操作(add,remove等) 都不与迭代器遍历做互斥,要自行维护互斥操作
 * 
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import day02.StrignDemo3;

/**
 * 将集合或map装环为线程安全的
 * 
 * ArrayList与linkedlist都不是安全的
 * 
 * @author b_anhr
 *
 */
public class syncDemo2 {

	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		
		list.add("123");
		list.add("12");
		list.add("123a");
		list.add("123d");

		System.out.println(list);
		
		list = Collections.synchronizedList(list);
		System.out.println(list);
		
		
	}

}
