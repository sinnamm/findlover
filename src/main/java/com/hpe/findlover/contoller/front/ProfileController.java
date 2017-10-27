package com.hpe.findlover.contoller.front;


import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.util.LoverUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.hpe.findlover.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


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
	private final LabelService labelService;

	@Autowired
	public ProfileController(UserService userBasicService, UserAssetService userAssetService, UserDetailService userDetailService, UserLifeService userLifeService, UserStatusService userStatusService, UserPickService userPickService, LabelService labelService) {
		this.userBasicService = userBasicService;
		this.userAssetService = userAssetService;
		this.userDetailService = userDetailService;
		this.userLifeService = userLifeService;
		this.userStatusService = userStatusService;
		this.userPickService = userPickService;
		this.labelService = labelService;
	}

	@GetMapping("{id}")
	public String getProfileById(@PathVariable("id") int userId, Model model) {
		UserBasic basic = userBasicService.selectByPrimaryKey(userId);
		if (basic.getAuthority() == 0) {
			logger.debug("ID为" + basic.getId() + "的用户个人资料所有人不可见");
		} else if (basic.getAuthority() == 2) {
			logger.debug("ID为" + basic.getId() + "的用户个人资料仅关注可见");
		}
		UserAsset asset = userAssetService.selectByPrimaryKey(userId);
		basic.setAge(LoverUtil.getAge(basic.getBirthday()));
		if (asset != null) {
			basic.setVip(LoverUtil.getDiffOfHours(asset.getVipDeadline()) > 0);
			basic.setStar(LoverUtil.getDiffOfHours(asset.getStarDeadline()) > 0);
		}
		model.addAttribute("basic", basic);
		model.addAttribute("detail", userDetailService.selectByPrimaryKey(userId));
		model.addAttribute("life", userLifeService.selectByPrimaryKey(userId));
		model.addAttribute("pick", userPickService.selectByPrimaryKey(userId));
		model.addAttribute("status", userStatusService.selectByPrimaryKey(userId));
		return "front/view_profile";
	}
}
