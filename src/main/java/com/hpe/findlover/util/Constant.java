package com.hpe.findlover.util;

import java.util.ArrayList;
import java.util.List;

public class Constant {

    //用于从数据表中查询出对应意义
    public static final String HIGH_SALARY = "high_salary";
    public static final String HIGH_EDUCATION = "high_education";
    public static final String HAVE_CAR = "have_car";
    public static final String HAVE_HOUSE = "have_house";
    public static final String CIVIL_SERVANT = "civil_servant";
    public static final String PROGRAMMER = "programmer";

    //用于添加标签时(已购房=>有车一族)，对应下拉框中选中值判断
    public static final String PURCHASED_HOUSE = "已购房";
    public static final String GOVERNMENT = "政府机构";
    public static final String COMPUTER_INTERNET = "计算机/互联网";
    public static List<String> education =null;

    static {
        education =  new ArrayList<>();
        education.add("大学本科");
        education.add("硕士");
        education.add("博士");
    }

}
