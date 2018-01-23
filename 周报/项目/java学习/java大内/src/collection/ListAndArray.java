package collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合  <-> 数组
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
	}

}
