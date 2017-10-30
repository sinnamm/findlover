package com.hpe.findlover;

import com.hpe.findlover.mapper.LabelMapper;
import com.hpe.findlover.mapper.UserBasicMapper;
import com.hpe.findlover.model.Label;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserPick;
import com.hpe.findlover.service.UserPickService;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.MD5Code;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {
	@Qualifier("DruidDataSource")
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserBasicMapper userBasicMapper;
	@Autowired
	private LabelMapper labelMapper;
	@Autowired
	private UserPickService userPickService;
	@Test
	public void getDatasource() {
		System.out.println(dataSource.getClass());
	}
	@Test
	public void getMD5(){
		System.out.println(new MD5Code().getMD5ofStr("123"));
	}
	@Test
	public void testMapper(){
		Label label = new Label();
		label.setName("测试1");
		System.err.println(labelMapper.insert(label));
		System.err.println("label Id:"+label.getId());
	}
	@Test
	public void insertPick(){
		UserBasic userBasic = new UserBasic();
		userBasic.setId(100046);
		userBasic.setAge(20);
		userBasic.setSexual("女");
		userBasic.setWorkplace("山东-济南");
		userBasic.setHeight(177);

		UserPick userPick = new UserPick();
		userPick.setId(userBasic.getId());
		userPick.setSex(userBasic.getSexual());
		userPick.setAgeLow(Math.max(18, LoverUtil.getAge(userBasic.getBirthday()) - 3));
		userPick.setAgeHigh(Math.min(66, LoverUtil.getAge(userBasic.getBirthday()) + 3));
		userPick.setWorkplace(userBasic.getWorkplace());
		userPick.setMarryStatus("未婚");
		userPick.setHeightLow(Math.max(145, userBasic.getHeight() - 10));
		userPick.setHeightHigh(Math.min(210, userBasic.getHeight() + 10));
		if (userPickService.insert(userPick)) {
			System.out.println("success");
		}else {
			System.out.println("error");
		}
	}

	@Test
	public void Pick(){
		//Integer id,String sex, Integer ageLow, Integer ageHigh, String workplace, String birthplace, String marryStatus, String education, Double salaryLow, Double salaryHigh, Integer heightLow, Integer heightHigh, String job, Integer drink, Integer smoke) {
		UserPick userPick = new UserPick(100046,"女",21,30,"山东-济南",null,null,null,null,null,160,180,null,null,null);
		userPickService.insertSelective(userPick);
	}
}
