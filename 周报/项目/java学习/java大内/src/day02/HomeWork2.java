package day02;

/**
 * 1、将字符串123,123,123,34,234,234按逗号分隔，并输出拆分后的每一项
 * 
 * 安正则表达式分隔字符串
 * split()
 * 
 * 2、将字符串123asdf123sdf456lkjj中的英文部分替换为“#char”
 * replaceAll()
 * 
 * 3、定义一个person类，重写toString方法，返回格式化字符串如：张三：15，5000
 * 		重写equals方法，要求名字相同就认为内容一致
 * 		
 * (重写的equal方法要注意复习)
 * 
 * @author b_anhr
 *
 */
public class HomeWork2 {

	public static void main(String[] args) {
		
		//section one
		String string = "123,123,123,34,234,234";
		
		String regex = ",";
		
		String string2[] = string.split(regex);

		for (String string3 : string2) {
			System.out.println(string3);
		}
		
		
		//section two
		String string3 = "123asdf123sdf456lkjj";
		
		String string4 = string3.replaceAll("[a-zA-Z]+", "#char");
		
		System.out.println(string4);
		
		//section three
		Person p1 = new Person();
		p1.setName("zhangsan");
		p1.setAge(12);
		p1.setSalary(3500);
		
		System.out.println(p1.toString());
		
		Person p2 = new Person();
		p2.setAge(14);
		p2.setName("zhangsan");
		p2.setSalary(10000);
		
		System.out.println(p1.equals(p2));
	}

}
