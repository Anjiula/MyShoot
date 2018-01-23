package collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ForEachDemo {

	public static void main(String[] args) {
		Collection<String> collection = new ArrayList<String>();
		
		collection.add("123");
		collection.add("#");
		collection.add("1231");
		collection.add("#");
		collection.add("12323");
		collection.add("#");
		collection.add("12");
		
		for (String string : collection) {
			System.out.println(string);
			if ("#".equals(string)) {
				
			}
		}
		
		Iterator<String> it = collection.iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			if ("#".equals(string)) {
				it.remove();
			}
		}
		
		System.out.println(collection);
		
		//泛型中的T必须是引用类型      
		Point<Integer> point = new Point<Integer>(2, 2);
		
		Point<Double> point2 = new Point<Double>(2.2, 2.1);
		
		//会报错    所以泛型要写类型，不写会当做obj来处理，下面类型转换会出错  如下报错：
//		Point<String> point3 = point2;
		Point point3 = point2;
		point3.setX(2.2);
		//从point3角度看，x，y都是obj，所以输出也是get到的是 (obj 强转 ->double)（不会不报错，会有警告）
		System.out.println(point3.getX() instanceof Double);
		
		//这一步会报错  	证明内部是使用obj，输出是强转为double
//		point2.setX(point3.getX());
		
		System.out.println(point);
		System.out.println(point2);
	}
	
	

}
