package collection;
/**
 * 
 * 集合存放元素时，使用的是元素的地址（引用）
 * 
 * 删除集合元素				boolean remove(E e)    只删除一个元素，  （删除第一个equal为true的元素）
 * 
 * 取并集      					addAll(c1)  
 * 
 * 判断是否包含给定集合所有元素  	 containsAll（c1）
 * 	
 * 删除两个集合中共有的元素  		removeAll(c1)
 * 
 * 
 */
import java.util.Collection;
import java.util.ArrayList;

public class CollectionDemo1 {

	public static void main(String[] args) {

		Collection collection = new ArrayList();
		
		
		/**
		 * boolean add(E e)
 		 * 向集合中添加元素
 		 * 
		 */
		collection.add("123");
		collection.add("12");
		
		System.out.println(collection);

		/**
		 * int size()
		 * 返回集合的元素个数
		 */
		System.out.println(collection.size());
		
		
		
		/**
		 * boolean isEmpty()
		 * 判空（true 为  空集合）
		 */
		System.out.println(collection.isEmpty());
		
		
		
		/**
		 * void clear()
		 * 清空集合
		 */
//		collection.clear();
//		System.out.println(collection.isEmpty());
		
		
		/**
		 * boolean contains(E e)
		 * 内部使用的是obj的equal比较，   如有需要可重写contains
		 */
		System.out.println(collection.contains("123"));
		
	}

}
