package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JInternalFrame;

public class HomeWork {

	public static void main(String[] args) {

		//1
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		
		list.add("one");
		list.add("two");
		list.add("three");

		list2.add("four");
		list2.add("five");
		list2.add("six");
		
		System.out.println(list);
		System.out.println(list2);
		
		list.addAll(list2);
		System.out.println(list);
		
		List<String> list3 = new ArrayList<String>();
		list3.add("one");
		list3.add("five");
		
		System.out.println(list.containsAll(list3));
		
		list.remove("two");
		System.out.println(list);
		
		//2
		List<String> list4 = new ArrayList<String>();
		
		list4.add("1");
		list4.add("#");
		list4.add("2");
		list4.add("#");
		list4.add("3");
		list4.add("#");
		list4.add("4");
		
		System.out.println(list4);
		
		Iterator<String> iterator = list4.iterator();
		
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			if ("#".equals(string)) {
				iterator.remove();
			}
		}
		System.out.println(list4);
		
		//3
		List<String> list5 = new ArrayList<String>();
		
		list5.add("one");
		list5.add("two");
		list5.add("three");
		list5.add("four");
		
		System.out.println(list5.get(1));
		list5.set(2, "3");
		list5.add(1, "2");
		list5.remove(2);
		System.out.println(list5);
		
		//4
		List<Integer> list6 = new ArrayList<Integer>();
		
		for (int i = 0; i < 10; i++) {
			list6.add(i);
		}
		
		System.out.println(list6);
		
		List<Integer> list7 = list6.subList(3, 7);
		
		for (int i = 0; i < list7.size(); i++) {
			list7.set(i, list7.get(i) * 10);
		}
		System.out.println(list6);
		
		list6.subList(7, 10).clear();
		
		System.out.println(list6);
	}

}
