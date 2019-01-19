package java8.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimeClock0 {
	
	
	public static void main(String[] args) {
//		testStringOrIntCompute();
	}

	private static void testStringOrIntCompute() {
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < 10000; i++) {
			Random rndYear=new Random();
	        int year=rndYear.nextInt(18)+2000;  //生成[2000,2017]的整数；年
	        Random rndMonth=new Random();
	        int month=rndMonth.nextInt(12)+1;   //生成[1,12]的整数；月
	        Random rndDay=new Random(); 
	        int day=rndDay.nextInt(30)+1;       //生成[1,30)的整数；日
			int val = year * 10000 + month * 100 + day;
			list.add(val);
		}
		long st = System.nanoTime();
		for(int i = 0; i < list.size(); i++) {
			int val = list.get(i);
//			stringParse(val);
			intParase(val);
		}
		long end = System.nanoTime();
		System.out.println("cost " + (end - st));
//		stringParse(val);
//		intParase(val);
	}

	private static void stringParse(int val) {
		String word = String.valueOf(val);
		int year = Integer.valueOf(word.substring(0,4));
		int month = Integer.valueOf(word.substring(4,6));
		int day = Integer.valueOf(word.substring(6));
//		isToday(day, month, year);
	}

	private static void intParase(int val) {
		int day = val % 100;
		int month = val % 10000 / 100;
		int year = val /10000;
//		isToday(day, month, year);
	}

	private static void isToday(int day, int month, int year) {
		LocalDate now = LocalDate.now();
		if(year == now.getYear() && month == now.getMonthValue() && day == now.getDayOfMonth()) {
			System.out.println("ok");
		}
	}

	public static long getClock0NextDay() {
		LocalDateTime current = LocalDateTime.of(LocalDate.now().minus(-1,ChronoUnit.DAYS),LocalTime.MIN);
        return current.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static long getClock0NextMonth() {
		LocalDateTime current = LocalDateTime.of(LocalDate.now().minus(-1,ChronoUnit.MONTHS).minus(-1,ChronoUnit.DAYS),LocalTime.MIN);
        return current.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static long getClock0NextWeek() {
		LocalDateTime current = LocalDateTime.of(LocalDate.now().minus(-1,ChronoUnit.WEEKS).minus(-1,ChronoUnit.DAYS),LocalTime.MIN);
        return current.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
}
