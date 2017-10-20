package com.hpe.findlover.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class LoverUtil {
	private static Logger logger = LogManager.getLogger(LoverUtil.class);

	/**
	 * @Author sinnamm
	 * @Describtion: 计算年龄的方法
	 * @Date Create in  21:36 2017/10/17
	 **/
	public static int getAge(Date birthday){
		int age = -1;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (birthday != null) {
			now.setTime(new Date());
			born.setTime(birthday);
			if (born.after(now)) {
				throw new IllegalArgumentException("年龄不能超过当前日期");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
			int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
			logger.info("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
			if (nowDayOfYear < bornDayOfYear) {
				age -= 1;
			}
		}

		return age;
	}

	/**
	 * @Author sinnamm
	 * @Describtion: 计算当前日期与指定日期的时间差，单位为小时，用于计算会员和星级的有效时间
	 * @Date Create in  21:12 2017/10/17
	 **/
	public static int getDiffOfHours(Date deadline) {
		return getDiff(TimeUnit.HOURS, deadline);
	}
	public static int getDiffOfDays(Date deadline) {
		return getDiff(TimeUnit.DAYS, deadline);
	}

	 /**
	 * @Author gss
	 * @Describtion: 计算当前日期与指定日期的时间差，单位由unit参数决定
	 **/
	public static int getDiff(TimeUnit unit,Date deadline){
		long now = unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		long dead = unit.convert(deadline.getTime(), TimeUnit.MILLISECONDS);
		return (int) (dead - now);
	}
	public static String getBasePath(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path ;
		return basePath;
	}
}
