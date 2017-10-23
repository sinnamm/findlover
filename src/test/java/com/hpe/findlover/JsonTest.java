package com.hpe.findlover;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hpe.findlover.util.LoverUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
//@SpringBootTest
public class JsonTest {
	@Test
	public void test1() {
		System.out.println(StringUtils.capitalize("reg_time"));
	}

	@Test
	public void data() throws Exception {
		File file = new File("D:/IdeaProjects/FindLover/项目资源/资源文件/findlover.xml");
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(file);
		JSONArray jsonArray = new JSONArray();
		Iterator<Element> iter = document.getRootElement().elementIterator();
		while (iter.hasNext()) {
			Element ele = iter.next();
			JSONObject jsonTable = new JSONObject();
			JSONObject cols = new JSONObject();
			Iterator<Element> colEles = ele.element("Columns").elementIterator("Column");
			while (colEles.hasNext()) {
				Element colEle = colEles.next();
				String[] arr = colEle.elementText("Code").split("_");
				Element comm = null;
				if ((comm = colEle.element("Comment"))!= null){
					String comment = comm.getText();
					JSONObject commJson = new JSONObject();
					if (arr.length > 1) {
						commJson.put("name", colEle.elementText("Name"));
					}else{
						commJson.put("name", colEle.elementText("Name"));
					}
					for (String s1 : comment.split("，")) {
						System.out.println("s1:"+s1);
						commJson.put(s1.split("：")[0], s1.split("：")[1]);
					}
					if (arr.length > 1) {
						cols.put(arr[0] + StringUtils.capitalize(arr[1]), commJson);
					} else {
						cols.put(arr[0], commJson);
					}
				}else {
					if (arr.length > 1) {
						cols.put(arr[0] + StringUtils.capitalize(arr[1]), colEle.elementText("Name"));
					} else {
						cols.put(arr[0], colEle.elementText("Name"));
					}
				}
			}
			jsonTable.put(ele.elementText("Code"), cols);
			jsonArray.add(jsonTable);
		}
		System.out.println(jsonArray.toJSONString());
	}

}
