package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.*;
import com.hpe.findlover.service.*;
import com.hpe.findlover.service.ComplainService;
import com.hpe.findlover.service.FollowService;
import com.hpe.findlover.util.Constant;
import com.hpe.findlover.util.LoverUtil;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("profile")
public class ProfileController {
	private Logger logger = LogManager.getLogger(ProfileController.class);
	private final UserService userBasicService;
	private final UserAssetService userAssetService;
	private final UserDetailService userDetailService;
	private final UserLifeService userLifeService;
	private final UserStatusService userStatusService;
	private final UserPickService userPickService;
	private final UserPhotoService userPhotoService;
	private final LabelService labelService;
	private final ComplainService complainService;
	private final FollowService followService;
	private final VisitTraceService visitTraceService;

	@Autowired
	public ProfileController(UserService userBasicService, UserAssetService userAssetService, UserDetailService userDetailService, UserLifeService userLifeService, UserStatusService userStatusService, UserPickService userPickService, UserPhotoService userPhotoService, LabelService labelService, ComplainService complainService, FollowService followService,VisitTraceService visitTraceService) {
		this.userBasicService = userBasicService;
		this.userAssetService = userAssetService;
		this.userDetailService = userDetailService;
		this.userLifeService = userLifeService;
		this.userStatusService = userStatusService;
		this.userPickService = userPickService;
		this.userPhotoService = userPhotoService;
		this.labelService = labelService;
		this.complainService = complainService;
		this.followService = followService;
		this.visitTraceService = visitTraceService;
	}

	@GetMapping("{id}")
	public String getProfileById(@PathVariable("id") int userId, Model model) {
		Integer sessionUserId = SessionUtils.getSessionAttr( "user", UserBasic.class).getId();
		//增加访问记录
		if (sessionUserId!=userId) {
			visitTraceService.insertSelective(new VisitTrace(null,sessionUserId, userId, new Date(),0));
		}

		UserBasic basic = userBasicService.selectByPrimaryKey(userId);
		if (basic.getAuthority() == Constant.PROFILE_AUTH_NONE) {
			logger.debug("ID为" + basic.getId() + "的用户个人资料所有人不可见");
		} else if (basic.getAuthority() == Constant.PROFILE_AUTH_FOLLOW) {
			logger.debug("ID为" + basic.getId() + "的用户个人资料仅关注可见");
		}
		userBasicService.userAttrHandler(basic);
		model.addAttribute("basic", LoverUtil.prettyDisplay(basic,UserBasic.class));
		model.addAttribute("detail", LoverUtil.prettyDisplay(userDetailService.selectByPrimaryKey(userId), UserDetail.class));
		model.addAttribute("life", LoverUtil.prettyDisplay(userLifeService.selectByPrimaryKey(userId),UserLife.class));
		model.addAttribute("pick", userPickService.selectByPrimaryKey(userId));
		model.addAttribute("status", LoverUtil.prettyDisplay(userStatusService.selectByPrimaryKey(userId),UserStatus.class));
		UserPhoto userPhoto = new UserPhoto();
		userPhoto.setUserId(basic.getId());
		model.addAttribute("userPhotos", userPhotoService.select(userPhoto));
		model.addAttribute("code", basic.getAuthority());
		Follow follow = new Follow();
		follow.setUserId(sessionUserId);
		follow.setFollowId(basic.getId());
		model.addAttribute("isFollow", followService.selectOne(follow) != null);
		//随机推荐星级会员
		List<UserBasic> stars = LoverUtil.getRandomStarUser(userPickService.selectByPrimaryKey(sessionUserId), 4, userBasicService);
		userBasicService.userAttrHandler(stars);
		model.addAttribute("stars", stars);
		return "front/view_profile";
	}

	@PostMapping("complain")
	@ResponseBody
	public boolean complain(Complain complain, HttpServletRequest request) {
		complain.setUserId(SessionUtils.getSessionAttr("user", UserBasic.class).getId());
		complain.setComTime(new Date());
		complain.setStatus(0);
		return complainService.insert(complain);
	}

}
