package com.hpe.findlover.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constant {

    /**
     * 首页固定显示16个推荐用户个数
     */
    public static final int INDEX_SHOW_USER_NUMBER=16;



    public static final String INIT_SIGNATURE="哇哦，该用户很懒，没有留下任何东西";
    /**
     * 广告位固定显示星级用户个数
     */
    public static final int SEARCH_SHOW_STAR_USER_NUMBER=4;

    public static final int SHOW_NUMBER = 5;

    /**
     * 用户动态的分类
     */
    public static final String HOT="hot";
    public static final String NEW="new";

    public static final int AGE_LOW_LIMIT = 18;
    public static final int AGE_HIGH_LIMIT = 66;
    public static final int HEIGHT_LOW_LIMIT = 145;
    public static final int HEIGHT_HIGH_LIMIT = 210;
    public static final int USER_DISABLED_STATUS = 2;
    public static final int USER_ACTIVE_STATUS = 1;
    public static final int USER_LOCKED_STATUS = 0;
    public static final int PROFILE_AUTH_ALL = 1;
    public static final int PROFILE_AUTH_NONE = 0;
    public static final int PROFILE_AUTH_FOLLOW = 2;

    //用于从数据表中查询出对应意义
    public static final String HIGH_SALARY = "high_salary";
    public static final String HIGH_EDUCATION = "high_education";
    public static final String HAVE_CAR = "have_car";
    public static final String HAVE_HOUSE = "have_house";
    public static final String CIVIL_SERVANT = "civil_servant";
    public static final String PROGRAMMER = "programmer";

    /**
     * 用于添加标签时(已购房=>有车一族)，对应下拉框中选中值判断
     */
    public static final String PURCHASED_HOUSE = "已购房";
    public static final String GOVERNMENT = "政府机构";
    public static final String COMPUTER_INTERNET = "计算机/互联网";
    public static List<String> education = Arrays.asList("大学本科","硕士","博士");

    /**
     * 默认用户那女头像
     */
    public static final String MALE_PHOTO = "group1/M00/00/00/rBEuvlnxjMuAQiedAABzD4s1V58372.png";
    public static final String FEMALE_PHOTO = "group1/M00/00/00/rBEuvlnxjQqAIjfAAAB54pr6RHc334.png";

}
