package java8.time;

import java.time.LocalDate;
import java.time.Period;

public class TimesCompute {

	public static void main(String[] args) {
		LocalDate l1 = LocalDate.of(2016, 10, 2);
		LocalDate l2 = LocalDate.of(2016, 9, 1);
		Period b = Period.between(l2, l1);
		 System.out.printf("年龄 : %d 年 %d 月 %d 日", b.getYears(), b.getMonths(), b.getDays());
	}

}
