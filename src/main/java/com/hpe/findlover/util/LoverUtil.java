package com.hpe.findlover.util;

import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserDetail;
import com.hpe.findlover.model.UserPick;
import com.hpe.findlover.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public final class LoverUtil {
	private static Logger logger = LogManager.getLogger(LoverUtil.class);

	public static String getMd5Password(String password,String salt) {
		return new Md5Hash(password,salt).toString();
	}

	/**
	 * 有关用户的pojo对象的漂亮显示，传入一个pojo类和这个类的Class对象，
	 * 返回和这个类各属性和值等价的的Map集合，自动处理null对象和null属性
	 *
     * @param obj
	 * @param clazz
	 * @return
	 */
	public static Map<String, Object> prettyDisplay(Object obj,Class<?> clazz) {
		if(obj == null) {
			obj = BeanUtils.instantiate(clazz);
		}
		String replacer = "-";
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		Map<String, Object> map = new HashMap<>(20);
		for (Field field : declaredFields) {
			field.setAccessible(true);
			Object value = null;
			String name = field.getName();
			try {
				value = field.get(obj);
			} catch (IllegalAccessException e) {
				logger.debug("属性" + name + "的值获取失败。");
				e.printStackTrace();
			}
			if("signature".equals(name) && value == null){
				map.put(name, Constant.INIT_SIGNATURE);
			}else {
				map.put(name, value == null ? replacer : value);
			}
		}
		return map;
	}

	/**
	 * 获取随机推荐给用户的星级用户
	 * @param userPick 用户择偶条件
	 * @param number 最多推荐个数
	 * @param userService 服务层接口
	 * @return
	 */
	public static List<UserBasic> getRandomStarUser(UserPick userPick,int number,UserService userService){
		if(userPick.getWorkplace()!=null) {
			userPick.setWorkProvince("%" + userPick.getWorkplace().split("-")[0] + "%");
		}
		logger.debug("userPick:"+userPick);
		List<UserBasic> userBasicStarList = userService.selectStarUser(userPick);
		List<UserBasic> userBasicStarPick = null;
		//如果用户数大于需要的人数则随机选四个用户显示
		if(userBasicStarList.size()> number){
			userBasicStarPick = LoverUtil.getRandomUser(userBasicStarList,number);
			logger.debug("用户条件的星级用户过多，需要随机选择");
		}else if (userBasicStarList.size() == 0){
			userBasicStarPick = LoverUtil.
					getRandomUser(userService.selectStarUser(new UserPick(userPick.getId(),userPick.getSex())),number);
			logger.debug("用户条件的星级用户没有，从所有星级用户随机选择");
		}else{
			userBasicStarPick=userBasicStarList;
			logger.debug("用户条件的星级用户有");
		}
		for (UserBasic userbasic : userBasicStarPick) {
			userbasic.setAge(LoverUtil.getAge(userbasic.getBirthday()));
		}
		return  userBasicStarPick;
	}

	/**
	 * 用户随机获取用户，从查询的数据从随机获取用户，需要结合getRandoms使用
	 * @param userBasicList 查询出来的集合，也就是需要从中挑选的结合
	 * @param num 随机挑选的个数
	 * @return
	 */
	public static <T> List<T> getRandomUser(List<T> userBasicList, int num)
	{
		int[] nums = getRandoms(0,userBasicList.size()-1,num);
		List<T> userBasics = new ArrayList<>();
		if(nums == null){
			return userBasics;
		}
		logger.debug("nums="+nums);
		for (int i=0;i<nums.length;i++){
			userBasics.add(userBasicList.get(nums[i]));
		}
		return userBasics;
	}



	/**
	 * 格式化userBasic的值，设置是否是VIP或者星级用户，设置默认的内心独白
	 * @param userBasicList
	 */
	public static void formatUserInfo(List<UserBasic> userBasicList){
		for (UserBasic userBasic:userBasicList) {
			userBasic.setAge(LoverUtil.getAge(userBasic.getBirthday()));
			if (userBasic.getUserAsset() != null) {
				userBasic.setVip(LoverUtil.getDiffOfHours(userBasic.getUserAsset().getVipDeadline())>0);
				userBasic.setStar(LoverUtil.getDiffOfHours(userBasic.getUserAsset().getStarDeadline())>0);
				logger.debug("用户名："+userBasic.getNickname()+"...是否是VIP："+userBasic.getVip()+"...是否是星级用户："+userBasic.getStar());
			}else {
				UserAsset userAsset = new UserAsset();
				userBasic.setVip(false);
				userBasic.setStar(false);
				userBasic.setUserAsset(userAsset);
			}
			if (userBasic.getUserDetail() != null) {
				if (userBasic.getUserDetail().getSignature()==null)
				{
					userBasic.getUserDetail().setSignature(Constant.INIT_SIGNATURE);
					logger.debug("用户名："+userBasic.getNickname()+"...内心独白："+userBasic.getUserDetail().getSignature());
				}
			}else {
				UserDetail userDetail = new UserDetail();
				userDetail.setSignature(Constant.INIT_SIGNATURE);
				userBasic.setUserDetail(userDetail);
			}
		}
	}

	/**
	 * 根据min和max随机生成一个范围在[min,max]的随机数，包括min和max
	 *
	 * @param min
	 * @param max
	 * @return int
	 */
	public static int getRandom(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	/**
	 * 根据min和max随机生成count个不重复的随机数组，用户随机选取用户显示
	 *
	 * @param min   随机数的范围最小值，一般是0开始
	 * @param max   随机数范围最大值，一般传入查询到的集合的长度-1
	 * @param count 需要随机数的个数
	 * @return int[] 返回的随机数数组
	 */
	public static int[] getRandoms(int min, int max, int count) {
		int[] randoms = new int[count];
		List<Integer> listRandom = new ArrayList<Integer>();

		if (count > (max - min + 1)) {
			return null;
		}
		// 将所有的可能出现的数字放进候选list
		for (int i = min; i <= max; i++) {
			listRandom.add(i);
		}
		// 从候选list中取出放入数组，已经被选中的就从这个list中移除
		for (int i = 0; i < count; i++) {
			int index = getRandom(0, listRandom.size() - 1);
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
	public static int getAge(Date birthday) {
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
			logger.debug("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
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

	public static int getDiffOfDays(Date start ,Date deadline) {
		return getDiff(TimeUnit.DAYS,start, deadline);
	}

	/**
	 * @Author gss
	 * @Describtion: 计算当前日期与指定日期的时间差，单位由unit参数决定
	 **/
	public static int getDiff(TimeUnit unit,Date end) {
		return getDiff(unit,new Date(),end);
	}
	public static int getDiff(TimeUnit unit,Date start ,Date end) {
		if (end.before(start)) {
			return 0;
		}
		return (int) (unit.convert(end.getTime()-start.getTime(), TimeUnit.MILLISECONDS));
	}

	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path;
		return basePath;
	}

	public static Date addMonth(Date baseDate, int month) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(baseDate);
		cld.add(Calendar.MONTH, month);
		return cld.getTime();
	}

	public static Date addDay(Date baseDate, int day) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(baseDate);
		cld.add(Calendar.DAY_OF_YEAR, day);
		return cld.getTime();
	}

}
