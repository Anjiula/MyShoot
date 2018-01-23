package collection;
/**
 * Iterator iterator()  及遍历
 * 
 * 迭代时不要使用集合方法去增删元素   foreach()也不能
 * 
 * hasNext()   remove()
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * collection提供了一种统一遍历的迭代器
 * 
 * Iterator iterator()
 * 获取用语遍历当前集合的迭代器
 * 
 * java.util.Iterator  是个接口     不同 的集合提供了相应的实现类
 * 
 * 遍历集合：问、取、（删）步骤			it.hasNext()
 * 
 * @author b_anhr
 *
 */
public class CollectionDemo2 {

	public static void main(String[] args) {
		
		Collection collection = new ArrayList();
		
		collection.add("123");
		collection.add("#");
		collection.add("12");
		collection.add("#");
		collection.add("124");
		System.out.println(collection);
		
		Iterator it = collection.iterator();

		while (it.hasNext()) {
			//这里会强转，最好存同一种类型
			String string = (String) it.next();
			if ("#".equals(string)) {
				/**
				 * 迭代时不要使用集合方法去增删元素
				 * 
				 * 详见java文件夹list
				 */
//				collection.remove(string);
				it.remove();
			}
		}
		System.out.println(collection);
	}

}
