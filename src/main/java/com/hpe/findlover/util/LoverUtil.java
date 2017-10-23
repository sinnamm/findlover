package com.hpe.findlover.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public final class LoverUtil {
	private static Logger logger = LogManager.getLogger(LoverUtil.class);

	/**
	 * 根据min和max随机生成一个范围在[min,max]的随机数，包括min和max
	 * @param min
	 * @param max
	 * @return int
	 */
	public  static  int getRandom(int min, int max){
		Random random = new Random();
		return random.nextInt( max - min + 1 ) + min;
	}

	/**
	 * 根据min和max随机生成count个不重复的随机数组，用户随机选取用户显示
	 * @param min 随机数的范围最小值，一般是0开始
	 * @param max 随机数范围最大值，一般传入查询到的集合的长度
	 * @param count 需要随机数的个数
	 * @return int[] 返回的随机数数组
	 */
	public static int[] getRandoms(int min, int max, int count){
		int[] randoms = new int[count];
		List<Integer> listRandom = new ArrayList<Integer>();

		if( count > ( max - min + 1 )){
			return null;
		}
		// 将所有的可能出现的数字放进候选list
		for(int i = min; i <= max; i++){
			listRandom.add(i);
		}
		// 从候选list中取出放入数组，已经被选中的就从这个list中移除
		for(int i = 0; i < count; i++){
			int index = getRandom(0, listRandom.size()-1);
			randoms[i] = listRandom.get(index);
			listRandom.remove(index);
		}

		return randoms;
	}

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
	public static Date addMonth(Date baseDate,int month){
		Calendar cld = Calendar.getInstance();
		cld.setTime(baseDate);
		cld.add(Calendar.MONTH, month);
		return cld.getTime();
	}
	public static Date addDay(Date baseDate,int day){
		Calendar cld = Calendar.getInstance();
		cld.setTime(baseDate);
		cld.add(Calendar.DAY_OF_YEAR, day);
		return cld.getTime();
	}
}
