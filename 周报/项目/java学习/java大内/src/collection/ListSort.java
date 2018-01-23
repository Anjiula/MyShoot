package collection;
/**
 * Collection	是集合的接口
 * Collections	是集合的工具类		Collections.sort(list)		
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ListSort {

	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			list.add(random.nextInt(100));
		}
		
		Collections.sort(list);

		System.out.println(list);
	}

}
