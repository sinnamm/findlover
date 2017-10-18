package com.hpe.findlover.util;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class LoverUtil {

	/**
	 * @Author sinnamm
	 * @Describtion: 计算年龄的方法
	 * @Date Create in  21:36 2017/10/17
	 **/
	public static int getAge(Date birthday){
		int age = 0;
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
			System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
			if (nowDayOfYear < bornDayOfYear) {
				age -= 1;
			}
		}
		return age;
	}

	/**
	 * @Author sinnamm
	 * @Describtion: 计算时间差的方法，用于计算会员和星级的有效时间
	 * @Date Create in  21:12 2017/10/17
	 **/
	public static int getTime(Date toDate) throws ParseException {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");//如2016-08-10 20:40
		String fromDate = simpleFormat.format(System.currentTimeMillis());
		long from = simpleFormat.parse(fromDate).getTime();
		long to = toDate.getTime();
		int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
		return days;
	}
	public static String getBasePath(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path ;
		return basePath;
	}
}
