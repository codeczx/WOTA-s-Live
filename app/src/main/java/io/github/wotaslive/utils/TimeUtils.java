package io.github.wotaslive.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by codeczx on 2017/11/15 23:02.
 * Class description:
 */
public class TimeUtils {

	public static String getTimeStamp(long msgTime) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long todayMidnightMillis = calendar.getTimeInMillis();
		boolean isToday = msgTime - todayMidnightMillis > 0;

		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
		long yesterdayMidnightMillis = calendar.getTimeInMillis();
		boolean isYesterday = msgTime - yesterdayMidnightMillis > 0 && msgTime - yesterdayMidnightMillis < 24 * 60 * 60 * 1000;

		if (isToday) {
			return getPrefix(msgTime - todayMidnightMillis) + millisToHourAndMin(msgTime - todayMidnightMillis);
		} else if (isYesterday) {
			return "昨天" + getPrefix(msgTime - yesterdayMidnightMillis) + millisToHourAndMin(msgTime - yesterdayMidnightMillis);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
			return sdf.format(new Date(msgTime));
		}
	}

	private static String getPrefix(long millis) {
		int hour = (int) (millis / (1000 * 60 * 60));
		hour = hour % 24;
		if (hour < 6 && hour >= 0) {
			return "凌晨";
		} else if (hour >= 6 && hour < 12) {
			return "早上";
		} else if (hour >= 12 && hour < 18) {
			return "下午";
		} else if (hour >= 18 && hour < 24) {
			return "晚上";
		}
		return null;
	}

	private static String millisToHourAndMin(long millis) {
		long hour = millis / (1000 * 60 * 60);
		long min = (millis - (hour * 60 * 60 * 1000)) / (1000 * 60);
		hour = hour == 24 ? 0 : hour;
		hour = hour % 12;
		return String.format(Locale.CHINA, "%02d:%02d", hour, min);
	}

	public static boolean isNeedShowTimeStamp(long first, long last) {
		long interval = Math.abs(first - last);
		return interval - (1000 * 60 * 5) > 0;
	}
}
