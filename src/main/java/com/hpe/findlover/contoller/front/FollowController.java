package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.Follow;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.FollowService;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.text.normalizer.UBiDiProps;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("follow")
public class FollowController {
	private Logger logger = LogManager.getLogger(FollowController.class);
	private final FollowService followService;

	@Autowired
	public FollowController(FollowService followService) {
		this.followService = followService;
	}

	@GetMapping
	public String index() {
		return "front/follow";
	}

	@GetMapping("followed/{followId}")
	@ResponseBody
	public boolean checkFollow(@PathVariable int followId,HttpServletRequest request) {
		Follow follow = new Follow();
		follow.setFollowId(followId);
		follow.setUserId(SessionUtils.getSessionAttr(request,"user", UserBasic.class).getId());
		return followService.selectOne(follow) != null;
	}


	@PostMapping
	@ResponseBody
	public boolean newFollow(Follow follow, HttpServletRequest request) {
		follow.setUserId(SessionUtils.getSessionAttr(request, "user", UserBasic.class).getId());
		logger.info("添加新关注：" + follow);
		if (followService.selectOne(follow) != null || SessionUtils.getSessionAttr(request, "user", UserBasic.class).getAuthority() != 1) {
			logger.debug("已关注或隐藏资料，关注失败！");
			return false;
		}
		follow.setFollowTime(new Date());
		return followService.insert(follow);
	}

	@DeleteMapping
	@ResponseBody
	public boolean cancelFollow(@RequestParam int followId, HttpServletRequest request) {
		Follow follow = new Follow();
		follow.setFollowId(followId);
		follow.setUserId(SessionUtils.getSessionAttr(request, "user", UserBasic.class).getId());
		logger.info("取消关注：" + follow);
		return followService.delete(follow) > 0;
	}
}
