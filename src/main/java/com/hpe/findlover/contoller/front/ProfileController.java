package com.hpe.findlover.contoller.front;

import com.hpe.findlover.contoller.back.UserControllerBack;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.back.*;
import com.hpe.findlover.util.LoverUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("profile")
public class ProfileController {
	private Logger logger = LogManager.getLogger(ProfileController.class);
	private final UserBasicServiceBack userBasicServiceBack;
	private final UserAssetServiceBack userAssetServiceBack;
	private final UserDetailServiceBack userDetailServiceBack;
	private final UserLifeServiceBack userLifeServiceBack;
	private final UserStatusServiceBack userStatusServiceBack;
	private final UserPickServiceBack userPickServiceBack;
	private final LabelServiceBack labelServiceBack;

	@Autowired
	public ProfileController(UserBasicServiceBack userBasicServiceBack, UserAssetServiceBack userAssetServiceBack, UserDetailServiceBack userDetailServiceBack, UserLifeServiceBack userLifeServiceBack, UserStatusServiceBack userStatusServiceBack, UserPickServiceBack userPickServiceBack, LabelServiceBack labelServiceBack) {
		this.userBasicServiceBack = userBasicServiceBack;
		this.userAssetServiceBack = userAssetServiceBack;
		this.userDetailServiceBack = userDetailServiceBack;
		this.userLifeServiceBack = userLifeServiceBack;
		this.userStatusServiceBack = userStatusServiceBack;
		this.userPickServiceBack = userPickServiceBack;
		this.labelServiceBack = labelServiceBack;
	}

	@GetMapping("{id}")
	public String getProfileById(@PathVariable("id") int userId, Model model) {
		UserBasic basic = userBasicServiceBack.selectByPrimaryKey(userId);
		if (basic.getAuthority() == 0) {
			logger.debug("ID为" + basic.getId() + "的用户个人资料所有人不可见");
		} else if (basic.getAuthority() == 2) {
			logger.debug("ID为" + basic.getId() + "的用户个人资料仅关注可见");
		}
		UserAsset asset = userAssetServiceBack.selectByPrimaryKey(userId);
		basic.setAge(LoverUtil.getAge(basic.getBirthday()));
		if (asset != null) {
			basic.setVip(LoverUtil.getDiffOfHours(asset.getVipDeadline()) > 0);
			basic.setStar(LoverUtil.getDiffOfHours(asset.getStarDeadline()) > 0);
		}
		model.addAttribute("basic", basic);
		model.addAttribute("detail", userDetailServiceBack.selectByPrimaryKey(userId));
		model.addAttribute("life", userLifeServiceBack.selectByPrimaryKey(userId));
		model.addAttribute("pick", userPickServiceBack.selectByPrimaryKey(userId));
		model.addAttribute("status", userStatusServiceBack.selectByPrimaryKey(userId));
		return "front/view_profile";
	}
}
