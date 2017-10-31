package com.hpe.findlover.contoller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.*;
import com.hpe.findlover.service.*;
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
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	@Autowired
	private FollowService followService;

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
		List<UserBasic> userBasicStarPick = LoverUtil.getRandomStarUser(userPick,Constant.SEARCH_SHOW_STAR_USER_NUMBER,userService);
		userBasicStarPick.forEach(logger::info);
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
		for (int i=0;i<list.size();i++){
			List<MessageReply> messageReplies = list.get(i).getReplies();
			for (int j=0;j<messageReplies.size();j++){
				messageReplies.get(j).setUserBasic(userService.selectByPrimaryKey(messageReplies.get(j).getUserId()));
			}
		}
		list.forEach(logger::info);
		PageInfo pageInfo = new PageInfo(list);
		return JSON.toJSONStringWithDateFormat(pageInfo,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
	}

	@GetMapping("/followMessage")
	@ResponseBody
	public String followMessage(@Param("pageNum")Integer pageNum,HttpServletRequest request) throws JsonProcessingException {
		logger.info("pageNum=="+pageNum);
		UserBasic userBasic = SessionUtils.getSessionAttr(request,"user",UserBasic.class);
		Set<Integer> followIds = followService.selectFollowIdByUserId(userBasic.getId());
		followIds.forEach(logger::info);
		PageHelper.startPage(pageNum,4,"pub_time desc");
		List<Message> list = messageService.selectMessageByFollow(followIds);
		for (int i=0;i<list.size();i++){
			List<MessageReply> messageReplies = list.get(i).getReplies();
			for (int j=0;j<messageReplies.size();j++){
				messageReplies.get(j).setUserBasic(userService.selectByPrimaryKey(messageReplies.get(j).getUserId()));
			}
		}
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
