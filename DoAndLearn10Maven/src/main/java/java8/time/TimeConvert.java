package java8.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeConvert {

	public static void main(String[] args) {
		Instant instant = Instant.ofEpochMilli(1546371743000L);
		ZoneId zone = ZoneId.systemDefault();
		LocalDate ld = LocalDateTime.ofInstant(instant, zone).toLocalDate();
		LocalDate now = LocalDate.now();
		
	}

	private static void longToLocalDateTime() {
		Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
		 ZoneId zone = ZoneId.systemDefault();
		 LocalDateTime dt = LocalDateTime.ofInstant(instant, zone);
	}

}
