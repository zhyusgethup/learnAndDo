package java8.time;

public class FastGet {

	public static long getBeforeHourClock(){
		long l = System.currentTimeMillis();
		return l - l % 3600000L;
	}
	public static void main(String[] args) {
		System.out.println(getBeforeHourClock());
	}

}
