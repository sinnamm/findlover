package com.hpe.findlover.contoller.back;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.SuccessStory;
import com.hpe.findlover.service.SuccessStoryService;
import com.hpe.findlover.service.UploadService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YYF;
 */
@Controller
@RequiresRoles("successStory")
@RequestMapping("admin/success_story")
public class SuccessStorysControllerBack {
	Logger logger= LoggerFactory.getLogger(SuccessStorysControllerBack.class);
	@Autowired
	SuccessStoryService successStoryService;
	@Autowired
	UploadService uploadService;
	@GetMapping
	public String successStory(){
		return "back/success_story/success_story";
	}
	@PostMapping("load_message")
	@ResponseBody
	public Object loadMessage(int status,int pageSize,int pageNum,String column,String keyword){
		logger.debug("status="+status+",pageSize="+pageSize+",pageNum="+pageNum+",colimn="+column+",keyword="+keyword);
		PageHelper.startPage(pageNum,pageSize,"success_time desc");
		List list = null;
		list=successStoryService.selectByKeywordAndStatus(column,keyword,status);
		PageInfo<SuccessStory> pageInfo = new PageInfo<>(list);
		return pageInfo ;
	}
	@PostMapping("alt_status")
	@ResponseBody
	public String altStatus(int storyId,int status){
		SuccessStory successStory= new SuccessStory();
		successStory.setStatus(status ^ 1);
		successStory.setId(storyId);
		if (successStoryService.updateByPrimaryKeySelective(successStory)){
			return "true";
		}
		return "false";
	}
	@PutMapping("info/{storyId}")
	@ResponseBody
	public String info(@PathVariable int storyId){
		SuccessStory successStory= new SuccessStory();
		successStory.setStatus(1);
		successStory.setId(storyId);
		if (successStoryService.updateByPrimaryKeySelective(successStory)){
			return "true";
		}
		return "false";
	}
	@DeleteMapping("delete/{storyId}")
	@ResponseBody
	public String delete(@PathVariable int storyId){
		if (successStoryService.deleteByPrimaryKey(storyId)>0){
			return "true";
		}
		return "false";
	}
	@GetMapping("success_story_detail/{id}")
	public String successStoryDetail(@PathVariable int id, Model model) throws  Exception{
		SuccessStory successStory=successStoryService.selectByPrimaryKey(id);
		String name=successStory.getContent();
		byte[] bytes = uploadService.downloadFile(name);
		String content = new String(bytes, "utf-8");
		model.addAttribute("successStory", content);
		model.addAttribute("successStoryObj", successStory);
		return "back/success_story/success_story_detail";
	}
}
