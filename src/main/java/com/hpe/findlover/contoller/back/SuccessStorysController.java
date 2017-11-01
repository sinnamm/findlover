package com.hpe.findlover.contoller.back;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Essay;
import com.hpe.findlover.model.SuccessStory;
import com.hpe.findlover.service.SuccessStoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.soap.Addressing;
import java.util.List;

/**
 * @author YYF;
 */
@Controller
@RequestMapping("admin/success_story")
public class SuccessStorysController {
	Logger logger= LoggerFactory.getLogger(SuccessStorysController.class);
	@Autowired
	SuccessStoryService successStoryService;
	@GetMapping
	public String successStory(){
		return "back/success_story/success_story";
	}
	@PostMapping("load_message")
	@ResponseBody
	public Object loadMessage(int status,int pageSize,int pageNum,String column,String keyword){
		logger.debug("status="+status+",pageSize="+pageSize+",pageNum="+pageNum+",colimn="+column+",keyword="+keyword);
		PageHelper.startPage(pageNum,pageSize);
		List list = null;
		list=successStoryService.selectByKeywordAndStatus(column,keyword,status);
		PageInfo<SuccessStory> pageInfo = new PageInfo<>(list);
		return pageInfo ;
	}
}
