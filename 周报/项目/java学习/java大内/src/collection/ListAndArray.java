package collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 集合  -> 数组			list.toArray(new T[])
 * 数组  -> 集合			Arrays.asList(a[])		(对转换后的要重新new一个进行操作     如下： )
 * 						List<String> list4 = new ArrayList<String>(Arrays.asList(string[]))
 * 
 * @author b_anhr
 *
 */
public class ListAndArray {

	public static void main(String[] args) {

		List<String> list = new ArrayList<String>();
		
		list.add("123");
		list.add("#");
		list.add("1231");
		list.add("#");
		list.add("12323");
		list.add("#");
		list.add("12");
		
		/**
		 * toArray() 有两种        带参数和不带参数的
		 * 不带参数的默认返回obj[]   
		 */

//		Object[] arrayObjects = list.toArray();
		
		String[] arrayStrings = list.toArray(new String[list.size()]);
		
		for (String string : arrayStrings) {
			System.out.println(string);
		}
		
		/**
		 * 数组 -> 集合
		 */
		List<String> list2 = Arrays.asList(arrayStrings);
		/**
		 * 想转换后的list  加一个元素 	  会抛异常
		 * 原因：修改list相当于修改原数组
		 */
		list2.set(1, "a");
		
		System.out.println(list2);
		/**
		 * 对集合的修改相当于修改原数组
		 */
		for (String string : arrayStrings) {
			System.out.println(string);
		}
		
		/**
		 * 所以要单独创建一个集合，去做一些操作
		 */
		List<String> list3 = new ArrayList<String>();
		
		list3.addAll(Arrays.asList(arrayStrings));
		
		/**
		 * 复制构造器     参数为集合类型
		 */
		List<String> list4 = new ArrayList<String>(Arrays.asList(arrayStrings));
	}

}
