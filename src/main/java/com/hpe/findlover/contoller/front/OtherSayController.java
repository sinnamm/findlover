package com.hpe.findlover.contoller.front;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.*;
import com.hpe.findlover.service.front.*;
import com.hpe.findlover.util.Constant;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.SessionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/***
 * @author  gss
 */
@Controller
@RequestMapping("other_says")
public class OtherSayController {
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserPickService userPickService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageLikeService messageLikeService;
	@Autowired
	private MessageReplyService messageReplyService;
	private Logger logger = LogManager.getLogger(this.getClass());
	private final MessageService messageService;

	@Autowired
	public OtherSayController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping
	public String otherSays(HttpServletRequest request,Model model) {
		UserBasic user = SessionUtils.getSessionAttr(request,"user",UserBasic.class);
		UserPick userPick = userPickService.selectByPrimaryKey(user.getId());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String dateStr = dateFormat.format(date);
		List<UserBasic> userBasicStarList = userService.selectStarUser(dateStr,userPick.getSex(),"%"+userPick.getWorkplace().substring(0,2)+"%");
		List<UserBasic> userBasicStarPick = null;
		//如果用户数大于4则随机选四个用户显示
		if(userBasicStarList.size()> Constant.SEARCH_SHOW_STAR_USER_NUMBER){
			userBasicStarPick = LoverUtil.getRandomUser(userBasicStarList,Constant.SEARCH_SHOW_STAR_USER_NUMBER);
		}else {
			userBasicStarPick=userBasicStarList;
		}
		for (UserBasic userbasic : userBasicStarPick) {
			userbasic.setAge(LoverUtil.getAge(userbasic.getBirthday()));
		}
		logger.info("userBasicStarPick....."+userBasicStarPick);
		model.addAttribute("userBasicStarPick",userBasicStarPick);
		return "front/other_says";
	}

	@PostMapping("/message")
	@ResponseBody
	public boolean insert(Message message, HttpServletRequest request) throws Exception {
		UserBasic user = SessionUtils.getSessionAttr(request, "user", UserBasic.class);
		assert user != null;
		message.setUserId(user.getId());
		message.setLikeCount(0);
		message.setReplyCount(0);
		message.setPubTime(new Date());
		logger.info("进行个人动态增加："+message);
		return messageService.insert(message);
	}
	@GetMapping("/message")
	@ResponseBody
	public String message(@Param("pageNum")Integer pageNum,@Param("type")String type) throws JsonProcessingException {
		logger.info("pageNum=="+pageNum+"......type=="+type);
		if(Constant.HOT.equals(type)){
			PageHelper.startPage(pageNum,4,"reply_count desc,like_count desc");
		}else if(Constant.NEW.equals(type)){
			PageHelper.startPage(pageNum,4,"pub_time desc");
		}
		List<Message> list = messageService.selectList();
		list.forEach(logger::info);
		PageInfo pageInfo = new PageInfo(list);
		return JSON.toJSONStringWithDateFormat(pageInfo,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
	}

	@GetMapping("/likeMessage/{messageId}")
	@ResponseBody
	public String likeMessage(@PathVariable("messageId") Integer messageId,HttpServletRequest request){
		Integer userId = SessionUtils.getSessionAttr(request,"user",UserBasic.class).getId();

		MessageLike old = messageLikeService.selectOne(new MessageLike(messageId,userId,null));
		if (old!=null){
			return "error";
		}
		MessageLike messageLike = new MessageLike(messageId,userId,new Date());
		boolean result = messageLikeService.insertSelective(messageLike);
		if (result) {
			return "success";
		}else {
			return "error";
		}
	}

	@GetMapping("/replyMessage")
	@ResponseBody
	public String replyMessage(@Param("reply")String reply,@Param("messageId")Integer messageId, HttpServletRequest request){
		logger.info("reply=="+reply+"messageId=="+messageId);
		Integer userId = SessionUtils.getSessionAttr(request,"user",UserBasic.class).getId();
		MessageReply messageReply = new MessageReply(messageId,userId,reply,new Date());
		boolean result = messageReplyService.insertSelective(messageReply);
		if (result) {
			return "success";
		}else {
			return "error";
		}
	}
}
