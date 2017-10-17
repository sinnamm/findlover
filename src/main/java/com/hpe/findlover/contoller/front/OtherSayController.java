package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.Message;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.MessageService;
import com.hpe.findlover.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("other_says")
public class OtherSayController {
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
		if (user == null)
			throw new Exception("Session中User数据为空！");
		message.setUserId(user.getId());
		message.setLikeCount(0);
		message.setReplyCount(0);
		message.setPubTime(new Date());
		return messageService.insert(message);
	}
}
