package java8.time;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeConvert {

	public static void main(String[] args) throws ParseException {
//		Instant instant = Instant.ofEpochMilli(1546371743000L);
//		ZoneId zone = ZoneId.systemDefault();
//		LocalDate ld = LocalDateTime.ofInstant(instant, zone).toLocalDate();
//		LocalDate now = LocalDate.now();
//		LocalDate ser = LocalDate.of(2019, 2, 1);
//		LocalDate role = LocalDate.of(2019, 2, 2);
//		ZoneId zone = ZoneId.systemDefault();
//		LocalDateTime t1 = LocalDateTime.of(role, LocalTime.MIN);
//		LocalDateTime t2 = LocalDateTime.of(ser, LocalTime.MIN);
//		long l1 = t1.atZone(zone).toInstant().toEpochMilli();
//		long l2 = t2.atZone(zone).toInstant().toEpochMilli();
//		System.out.println(l1);
//		System.out.printf("l1 = %d, l2 = %d l1-l2=%d", l1, l2,(l1 - l2));
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = simpleDateFormat.parse("2019-02-01");
		System.out.println(date.getTime());
		System.out.println(date);
	}

	private static void longToLocalDateTime() {
		Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
		 ZoneId zone = ZoneId.systemDefault();
		 LocalDateTime dt = LocalDateTime.ofInstant(instant, zone);
	}

}
