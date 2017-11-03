package com.hpe.findlover.contoller.front;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Follow;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserPick;
import com.hpe.findlover.service.*;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("follow")
public class FollowController {
	private Logger logger = LogManager.getLogger(FollowController.class);
	private final FollowService followService;
	private final UserService userService;
	private final UserPickService userPickService;
	private final LetterService letterService;
	private final NoticeService noticeService;
	private final VisitTraceService visitTraceService;

	@Autowired
	public FollowController(FollowService followService, UserService userService, UserPickService userPickService, LetterService letterService,VisitTraceService visitTraceService,NoticeService noticeService) {
		this.followService = followService;
		this.userService = userService;
		this.userPickService = userPickService;
		this.letterService = letterService;
		this.visitTraceService = visitTraceService;
		this.noticeService = noticeService;
	}

	@GetMapping
	public String index(Model model, HttpSession session) {
		UserBasic userBasic = ((UserBasic) session.getAttribute("user"));
		int userId = userBasic.getId();
		UserPick userPick = userPickService.selectByPrimaryKey(userId);
		List<UserBasic> stars = LoverUtil.getRandomStarUser(userPick, 4, userService);
		userService.userAttrHandler(stars);
		model.addAttribute("stars", stars);
		logger.debug("stars: " + stars);
		//右侧信息条数
		model.addAttribute("letterCount", letterService.selectUnreadCount(userId));
		model.addAttribute("followCount", followService.selectFollowCount(userId));
		model.addAttribute("noticeCount", noticeService.selectUnReadNotice(userBasic).size());
		model.addAttribute("visitTraceCount",visitTraceService.selectUnreadCount(userId));
		// todo visit_record and notice count
		return "front/follow";
	}

	@GetMapping("followed/{followId}")
	@ResponseBody
	public boolean checkFollow(@PathVariable int followId, HttpServletRequest request) {
		Follow follow = new Follow();
		follow.setFollowId(followId);
		follow.setUserId(SessionUtils.getSessionAttr("user", UserBasic.class).getId());
		return followService.selectOne(follow) != null;
	}


	@PostMapping
	@ResponseBody
	public boolean newFollow(Follow follow, HttpServletRequest request) {
		follow.setUserId(SessionUtils.getSessionAttr("user", UserBasic.class).getId());
		logger.info("添加新关注：" + follow);
		if (followService.selectOne(follow) != null || SessionUtils.getSessionAttr("user", UserBasic.class).getAuthority() != 1) {
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
		follow.setUserId(SessionUtils.getSessionAttr( "user", UserBasic.class).getId());
		logger.info("取消关注：" + follow);
		return followService.delete(follow) > 0;
	}

	@PostMapping("myfollow")
	@ResponseBody
	public String myFollowList(Page<UserBasic> page, HttpServletRequest request) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), "follow_time desc");
		List<UserBasic> follows = followService.selectAllFollow(SessionUtils.getSessionAttr( "user", UserBasic.class).getId());
		PageInfo<UserBasic> pageInfo = new PageInfo<>(follows);
		userService.userAttrHandler(follows);
		logger.debug("MyFollow: " + JSON.toJSONString(pageInfo));
		return JSON.toJSONStringWithDateFormat(pageInfo, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
	}

	@PostMapping("follower")
	@ResponseBody
	public String follower(Page<UserBasic> page, HttpServletRequest request) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize(), "follow_time desc");
		List<UserBasic> followers = followService.selectAllFollower(SessionUtils.getSessionAttr( "user", UserBasic.class).getId());
		PageInfo<UserBasic> pageInfo = new PageInfo<>(followers);
		userService.userAttrHandler(followers);
		return JSON.toJSONStringWithDateFormat(pageInfo, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
	}

}
