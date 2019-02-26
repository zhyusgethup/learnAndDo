package basic.words;

public class FunctionsInOneWordOrder {
	public static void main(String[] args) {
		A.instance.a(f1(f2(f3(f4(1)))));
	}
	public static class A {
		static A instance = new A();
		A() {
			System.out.println("A");
		}
		public void a(int a) {
			System.out.println("a");
		}
	}
	
	public static int f1(int a1) {
		System.out.println("f1");
		return a1;
	}
	public static int f2(int a1) {
		System.out.println("f2");
		return a1;
	}
	public static int f3(int a1) {
		System.out.println("f3");
		return a1;
	}
	public static int f4(int a1) {
		System.out.println("f4");
		return a1;
	}
	public static int f5(int a1) {
		System.out.println("f5");
		return a1;
	}
	public static int f6(int a1) {
		System.out.println("f6");
		return a1;
	}
	public static int f7(int a1) {
		System.out.println("f7");
		return a1;
	}
	public static int f8(int a1) {
		System.out.println("f8");
		return a1;
	}
	public static int f9(int a1) {
		System.out.println("f9");
		return a1;
	}
}
