package day04_file;
/**
 * 
 * 影响性能条件: map中的key一样,但是hashcode不一样,这样会导(致键值对构成的列队)替换为(原来键值对)的空间
 * 
 * 当自己的类要作为hashmap的key存储,要求那个类重写equal() &  hashcode()方法
 * 
 * 详见图片hashMap图片详解.jpg
 * 
 * map优化 : 大数据存储查询,推荐使用初始化容量,最佳是散列数组的3/4
 * 
 * (若散列表中增加的数据/池子里的容量(初始容量)(散列数组大小)(bucket的数量)=0.75时,  会扩容并重新散列,(rehash)    初始容量默认为16(条))
 */
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

/**
 * java.util.map   相当于字典
 * 
 * key不能重复    （equals判断）
 * 
 * 常用的实现类为hashmap
 * 
 * put()   		加入  替换 		返回原来value
 * remove()		删除				返回原来value
 * 
 * 遍历
 * 
 * @author b_anhr
 *
 */
public class MapDemo {

	public static void main(String[] args) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		
		//put返回为null  但编译器会自动拆箱为int  这时会null.intValue()   会报错    空指针异常
		//int i = map.put("语文", 130);
		//这样不会，
		Integer integer = map.put("语文", 130);
		
		map.put("语文", 130);
		map.put("数学", 130);
		map.put("英文", 130);
		
		System.out.println(map);
		
		map.put("语文", 120);
		System.out.println(map);
		
		System.out.println(map.remove("数学"));
		System.out.println(map);
		
		
		/**
		 * 遍历map三种方式
		 * 遍历key
		 * 遍历key-value
		 * 遍历value（不常用）
		 */
		
		/**
		 * 遍历key
		 * Set<k> keySet()
		 * 该方法会将当前map中所有key存入一个set集合后返回,然后遍历该set
		 */
		for (String key : map.keySet()) {
			System.out.println(key);
			System.out.println(map.get(key));
		}
		
		/**
		 * 遍历key-value
		 * map中每一组键值对都是有map的内部类entry的实例表示的
		 * entry有两个方法:getKey getValue
		 * 
		 * set<Entry> entrySet
		 * 该方法会将map中的每一组键值对(Entry实例)存入set中返回
		 */
		Set<Entry<String, Integer>> set = map.entrySet();
		
		for (Entry<String, Integer> entry : set) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
		
		/**
		 * 遍历value（不常用）
		 * collection values()
		 * 该方法会返回当前map中所有的value存入一个集合
		 */
		Collection<Integer> list = map.values();
		for (Integer integer2 : list) {
			System.out.println(integer2);
		}
	}

}
