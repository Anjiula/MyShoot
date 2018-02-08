package day04_file;
/**
 * sort()  		comparable接口		cho()
 * 
 * 侵入性 
 * 想实现某种功能  ，而去修改的程序的现象， 叫侵入性，侵入性越强，越不好
 * 
 * 重新定义比较的三种方法
 * 匿名内部类（推荐）   、定义新类型（需要很多地方用到）  、 重写比较方法（不推荐）
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortListDemo1 {

	public static void main(String[] args) {

		List<Point> list = new ArrayList<Point>();
		
		list.add(new Point(1, 2));
		list.add(new Point(4, 2));
		list.add(new Point(3, 2));
		list.add(new Point(2, 2));
		list.add(new Point(2, 1));

		/**
		 * collections类不知如何比较大小
		 * sort（）方法要求集合元素必须实现comparable接口	该接口用于规定实现类是可以比较的
		 * 
		 * 其中有一个cho方法用来定义比较大小规则
		 */
		Collections.sort(list);
		
		List<String> list2 = new ArrayList<String>();
		
		list2.add("adsf");
		list2.add("asdxcvc");
		list2.add("adsfghjkk");
		list2.add("adsferrt");
		list2.add("adsfasdf");
		
		/**
		 * 使用匿名内部类
		 */
		Comparator<String> myComparator2 = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
			
		};
		
		Collections.sort(list2, new myComparator());
		Collections.sort(list2, myComparator2);
		
		System.out.println(list);
		System.out.println(list2);
	}
	
}


/**
 * 定义一个额外的比较器
 * 
 * @author b_anhr
 *
 */
class myComparator implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		return o1.length() - o2.length();
	}
	
}
