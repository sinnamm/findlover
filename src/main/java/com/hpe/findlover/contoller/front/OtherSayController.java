package com.hpe.findlover.contoller.front;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Message;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.MessageService;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("other_says")
public class OtherSayController {
	@Autowired
	private MessageSource messageSource;
	private Logger logger = LogManager.getLogger(this.getClass());
	private final MessageService messageService;

	@Autowired
	public OtherSayController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping
	public String otherSays() {
		return "front/other_says";
	}

	@PostMapping("message")
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
	@GetMapping("message")
	@ResponseBody
	public String list(Page<Message> page,@RequestParam String type) throws JsonProcessingException {
		if(type.equals("hot")){
			page.setOrderBy("reply_count,like_count desc");
		}else if(type.equals("new")){
			page.setOrderBy("pub_time desc");
		}
		PageHelper.startPage(page.getPageNum(),page.getPageSize(),page.getOrderBy());
		List<Message> list = messageService.selectList();
		return JSONObject.toJSON(new PageInfo<>(list)).toString();
	}
}
