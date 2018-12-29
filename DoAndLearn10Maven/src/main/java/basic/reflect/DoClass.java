package basic.reflect;

public class DoClass {

	public static void main(String[] args) {
		Class zClass = String.class;
		String string = "!@3213";
		System.out.println(string.getClass() == zClass);
	}

}
