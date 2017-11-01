package com.hpe.findlover;

import com.hpe.findlover.util.LoverUtil;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
//@SpringBootTest
public class MethodTest {

    @Test
    public void getDate(){
        int count=5,max=10,min=0;
        int[] randoms = new int[count];
        List<Integer> listRandom = new ArrayList<Integer>();

        if( count > ( max - min + 1 )){
            System.out.println("run");
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

        for (int mun :
                randoms) {
            System.out.println(mun);
        }
    }

    /**
     * 根据min和max随机生成一个范围在[min,max]的随机数，包括min和max
     * @param min
     * @param max
     * @return int
     */
    public int getRandom(int min, int max){
        Random random = new Random();
        return random.nextInt( max - min + 1 ) + min;
    }

    @Test
    public void data() throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");//如2016-08-10 20:40
        String fromDate = "2017-12-10 16:55:21";
        String toDate = simpleFormat.format(System.currentTimeMillis());
        long from = simpleFormat.parse(fromDate).getTime();
        long to = simpleFormat.parse(toDate).getTime();
        int days = (int) ((to - from)/(1000 * 60 * 60 * 24));

        System.out.println(days);
    }
    @Test
    public void shiroMD5Test(){
        Md5Hash hash = new Md5Hash("123", "caocao5@163.com");
        System.out.println(hash.toString());
    }

    @Test
    public void ageTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD");
        Date date = simpleDateFormat.parse("1996-01-22");
        int age = getAge(date);
        System.out.println(age);
    }

    public int getAge(Date birthday){
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (birthday != null) {
            now.setTime(new Date());
            born.setTime(birthday);
            if (born.after(now)) {
                throw new IllegalArgumentException("出生日期不能超过当前日期");
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
    @Test
    public void testBool(){
    }
    @Test
    public void beanUtilsTest() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Student s = new Student(112, "gss", null, new Date());
        Field[] declaredFields = s.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object obj = field.get(s);
            map.put(field.getName(), obj ==null?"-":obj);
        }
        System.out.println(map);
    }
    static class Student{
        private Integer sid;
        private String name;
        private Double score;
        private Date birthday;
        private boolean flag;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public Student(Integer sid, String name, Double score, Date birthday) {
            this.sid = sid;
            this.name = name;
            this.score = score;
            this.birthday = birthday;
        }

        public Student() {

        }

        public Integer getSid() {
            return sid;
        }

        public void setSid(Integer sid) {
            this.sid = sid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
    }
}
