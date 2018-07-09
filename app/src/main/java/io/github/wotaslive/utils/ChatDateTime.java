package io.github.wotaslive.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChatDateTime {
	private static final int DAY = (60 * 60 * 24) * 1000;

	/**
	 * 获取当天0点的时间点
	 *
	 * @return 时间点
	 */
	private static Long getStartTimeOfDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}

	/**
	 * 获取日期的星期数
	 *
	 * @param dt 日期
	 * @return 星期
	 */
	private static String getWeekOfDate(Date dt) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取显示的时间描述文字
	 *
	 * @param time 需要转换的时间
	 * @return 时间描述
	 */
	public static String getNiceTime(Long time) {
		String pointText;
		Date date = new Date(time);
		DateFormat df;
		if (time < getStartTimeOfDay()) {
			if (time >= getStartTimeOfDay() - DAY) {
				//昨天
				df = new SimpleDateFormat("HH:mm", Locale.US);
				pointText = "昨天 " + df.format(date);
				return pointText;
			}
			else if (time >= getStartTimeOfDay() - 6 * DAY) {
				//近7天
				df = new SimpleDateFormat("HH:mm", Locale.US);
				return getWeekOfDate(date) + " " + df.format(date);
			}
			else {
				//7天以前
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
				pointText = df.format(date);
				return pointText;
			}

		}
		else {
			//今天
			df = new SimpleDateFormat("HH:mm", Locale.US);
			pointText = df.format(date);
			return pointText;
		}
	}
}
