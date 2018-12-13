package dataStruct.date;

import java.sql.Date;

import dataStruct.date.cons.Cons;

public class TimeStruckCount {

	public static void main(String[] args) {
		long time = 1543396958537L;
		Date date = new Date(time);
		System.out.println(Cons.sdf.format(date));
	}
}
