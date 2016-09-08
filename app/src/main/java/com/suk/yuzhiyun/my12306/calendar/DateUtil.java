package com.suk.yuzhiyun.my12306.calendar;

import java.util.Calendar;

/**
 * 用于处理日期工具类
 *
 * @author xiejinxiong
 *
 */
public class DateUtil {

	/**
	 * 获取当前年份
	 *
	 * @return
	 */
	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 获取当前月份
	 *
	 * @return
	 */
	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;// +1是因为返回来的值并不是代表月份，而是对应于Calendar.MAY常数的值，
		// Calendar在月份上的常数值从Calendar.JANUARY开始是0，到Calendar.DECEMBER的11
	}

	/**
	 * 获取当前的时间为该月的第几天
	 *
	 * @return
	 */
	public static int getCurrentMonthDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前的时间为该周的第几天
	 *
	 * @return
	 */
	public static int getWeekDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取当前时间为该天的多少点
	 *
	 * @return
	 */
	public static int getHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		// Calendar calendar = Calendar.getInstance();
		// System.out.println(calendar.get(Calendar.HOUR_OF_DAY)); // 24小时制
		// System.out.println(calendar.get(Calendar.HOUR)); // 12小时制
	}

	/**
	 * 获取当前的分钟时间
	 *
	 * @return
	 */
	public static int getMinute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 通过获得年份和月份确定该月的日期分布
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int[][] getMonthNumFromDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);// -1是因为赋的值并不是代表月份，而是对应于Calendar.MAY常数的值，

		int days[][] = new int[6][7];// 存储该月的日期分布

		int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得该月的第一天位于周几（需要注意的是，一周的第一天为周日，值为1）

		int monthDaysNum = getMonthDaysNum(year, month);// 获得该月的天数
		// 获得上个月的天数
		int lastMonthDaysNum = getLastMonthDaysNum(year, month);

		// 填充本月的日期
		int dayNum = 1;
		int lastDayNum = 1;
		for (int i = 0; i < days.length; i++) {
			for (int j = 0; j < days[i].length; j++) {
				if (i == 0 && j < firstDayOfWeek - 1) {// 填充上个月的剩余部分
					days[i][j] = lastMonthDaysNum - firstDayOfWeek + 2 + j;
				} else if (dayNum <= monthDaysNum) {// 填充本月
					days[i][j] = dayNum++;
				} else {// 填充下个月的未来部分
					days[i][j] = lastDayNum++;
				}
			}
		}

		return days;

	}

	/**
	 * 根据年数以及月份数获得上个月的天数
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getLastMonthDaysNum(int year, int month) {

		int lastMonthDaysNum = 0;

		if (month == 1) {
			lastMonthDaysNum = getMonthDaysNum(year - 1, 12);
		} else {
			lastMonthDaysNum = getMonthDaysNum(year, month - 1);
		}
		return lastMonthDaysNum;

	}

	/**
	 * 根据年数以及月份数获得该月的天数
	 *
	 * @param year
	 * @param month
	 * @return 若返回为负一，这说明输入的年数和月数不符合规格
	 */
	public static int getMonthDaysNum(int year, int month) {

		if (year < 0 || month <= 0 || month > 12) {// 对于年份与月份进行简单判断
			return -1;
		}

		int[] array = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };// 一年中，每个月份的天数

		if (month != 2) {
			return array[month - 1];
		} else {
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// 闰年判断
				return 29;
			} else {
				return 28;
			}
		}

	}
}
