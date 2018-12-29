package java8.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/***
 * 时间验证 要求
 * 
 * @author Zeng Haoyu 2018年12月28日 上午10:08:42
 */
public class TimeChecker {

	public static void main(String[] args) {
		long lastFreshTime = 1546012800000L;
		// long lastFreshTime = 1545926399000L;// 昨晚 11点59 compareTo = 1;
		// long lastFreshTime = 1545926400000L;//今天凌晨 compareTo = 0;
		// int[] refreshTime = new int[]{12};
		// System.out.println(checkRefreshTime(lastFreshTime, refreshTime));
		System.out.println((checkRefreshTime(System.currentTimeMillis() - 1000 * 3600 * 48,0 )));
//		System.out.println((checkRefreshTime(System.currentTimeMillis(),0
//			)));
	}

	private static long checkRefreshTime(long lastRefreshTime, int refreshTime) {
		// 如果没有刷新时间,不刷新
		if (refreshTime < 0)
			return 0L;
		// 当前日期
		Clock system = Clock.systemDefaultZone();
		// 未来时间直接返回false
		if (lastRefreshTime > system.millis())
			return 0;

		LocalDateTime current = LocalDateTime.now(system);
		// 上一次刷新日期
		LocalDateTime last = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastRefreshTime), ZoneId.systemDefault());
		int compareTo = current.toLocalDate().compareTo(last.toLocalDate());
		System.out.println(compareTo);
		if (compareTo > 0) {
			// 当前时间超过了刷新时间 直接刷新
			if (current.getHour() >= refreshTime ||
			// 如果刷新时间间隔越过1天 直接刷新
					current.minusDays(1).toLocalDate().isAfter(last.toLocalDate()) ||
					// 是昨天
					last.getHour() < refreshTime) {
				LocalDateTime nn = current.withHour(0).withMinute(0).withSecond(0);
				return nn.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			}
			// 如果是同一天
		} else if (compareTo == 0) {
			// 相等 同一天
			// 下一个刷新时间点
			int nextRefreshTime = -1;
			LocalTime localTime = LocalTime.of(refreshTime, 0, 0, 0);
			// 查找下一个刷新时间点
			if (last.toLocalTime().isBefore(localTime)) {
				// 下个刷新点
				nextRefreshTime = refreshTime;
				// 查找结束
				
				
			}
			// 如果有下一个刷新时间点
			if (nextRefreshTime > -1 && current.getHour() >= nextRefreshTime) {
				LocalDateTime nn = current.withHour(0).withMinute(0).withSecond(0);
				return nn.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();	
			}
		} else {
			// 未来时间 不刷新
			return 0;
		}
		return 0;
	}

	public static long get0HourOfToday() {

		long now = System.currentTimeMillis();
		long daySecond = 60 * 60 * 24 * 1000L;
		long dayTime = now - (now) % daySecond;
		System.out.println(new Date(dayTime));
		return dayTime;
	}

	/**
	 * 检测是否达到刷新时间
	 *
	 * @param lastRefreshTime
	 *            上一次刷新时间
	 * @param refreshTime
	 *            需要刷新的时间点
	 * @return 是否可以刷新
	 */
	private static boolean checkRefreshTime(long lastRefreshTime, int[] refreshTime) {
		// 如果没有刷新时间,不刷新
		if (null == refreshTime || refreshTime.length == 0)
			return false;
		// 当前日期
		Clock system = Clock.systemDefaultZone();
		// 未来时间直接返回false
		if (lastRefreshTime > system.millis())
			return false;

		LocalDateTime current = LocalDateTime.now(system);
		// 上一次刷新日期
		LocalDateTime last = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastRefreshTime), ZoneId.systemDefault());
		int compareTo = current.toLocalDate().compareTo(last.toLocalDate());
		System.out.println(compareTo);
		if (compareTo > 0) {
			// 当前时间超过了刷新时间 直接刷新
			return current.getHour() >= refreshTime[0] ||
			// 如果刷新时间间隔越过1天 直接刷新
					current.minusDays(1).toLocalDate().isAfter(last.toLocalDate()) ||
					// 是昨天
					last.getHour() < refreshTime[refreshTime.length - 1];
			// 如果是同一天
		} else if (compareTo == 0) {
			// 相等 同一天
			// 下一个刷新时间点
			int nextRefreshTime = -1;
			// 检测所有时间点是否已刷新
			for (int time : refreshTime) {
				// 刷新时间
				LocalTime localTime = LocalTime.of(time, 0, 0, 0);
				// 查找下一个刷新时间点
				if (last.toLocalTime().isBefore(localTime)) {
					// 下个刷新点
					nextRefreshTime = time;
					// 查找结束
					break;
				}
			}
			// 如果有下一个刷新时间点
			return nextRefreshTime > -1 && current.getHour() >= nextRefreshTime;
		} else {
			// 未来时间 不刷新
			return false;
		}
	}
}
