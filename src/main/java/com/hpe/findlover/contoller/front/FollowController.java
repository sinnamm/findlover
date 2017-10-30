package com.hpe.findlover.contoller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Follow;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.UserAssetService;
import com.hpe.findlover.service.UserService;
import com.hpe.findlover.service.FollowService;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("follow")
public class FollowController {
	private Logger logger = LogManager.getLogger(FollowController.class);
	private final FollowService followService;
	private final UserService userService;

	@Autowired
	public FollowController(FollowService followService, UserService userService) {
		this.followService = followService;
		this.userService = userService;
	}

	@GetMapping
	public String index() {
		return "front/follow";
	}

	@GetMapping("followed/{followId}")
	@ResponseBody
	public boolean checkFollow(@PathVariable int followId, HttpServletRequest request) {
		Follow follow = new Follow();
		follow.setFollowId(followId);
		follow.setUserId(SessionUtils.getSessionAttr(request, "user", UserBasic.class).getId());
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

	@PostMapping("myfollow")
	@ResponseBody
	public String myFollowList(Page<UserBasic> page, HttpServletRequest request) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), "follow_time desc");
		List<UserBasic> follows = followService.selectAllFollow(SessionUtils.getSessionAttr(request, "user", UserBasic.class).getId());
		PageInfo<UserBasic> pageInfo = new PageInfo<>(follows);
		userService.userAttrHandler(follows);
		logger.debug("MyFollow: " + JSON.toJSONString(pageInfo));
		return JSON.toJSONStringWithDateFormat(pageInfo, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
	}

	@PostMapping("follower")
	@ResponseBody
	public String follower(Page<UserBasic> page, HttpServletRequest request) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), "follow_time desc");
		List<UserBasic> followers = followService.selectAllFollower(SessionUtils.getSessionAttr(request, "user", UserBasic.class).getId());
		PageInfo<UserBasic> pageInfo = new PageInfo<>(followers);
		userService.userAttrHandler(followers);
		return JSON.toJSONStringWithDateFormat(pageInfo, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
	}

}
