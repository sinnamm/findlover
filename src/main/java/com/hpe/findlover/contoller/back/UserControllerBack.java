package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserDetail;
import com.hpe.findlover.service.BaseService;
import com.hpe.findlover.service.back.*;
import com.hpe.findlover.util.LoverUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.List;

/**
 * @author Gss
 */
@Controller
@RequestMapping("admin/user")
public class UserControllerBack {
	private Logger logger = LogManager.getLogger(UserControllerBack.class);
	private final UserBasicServiceBack userBasicServiceBack;
	private final UserAssetServiceBack userAssetServiceBack;
	private final UserDetailServiceBack userDetailServiceBack;
	private final UserLifeServiceBack userLifeServiceBack;
	private final UserStatusServiceBack userStatusServiceBack;
	private final UserPickServiceBack userPickServiceBack;

	@Autowired
	public UserControllerBack(UserBasicServiceBack userBasicServiceBack, UserAssetServiceBack userAssetServiceBack, UserDetailServiceBack userDetailServiceBack, UserLifeServiceBack userLifeServiceBack, UserStatusServiceBack userStatusServiceBack, UserPickServiceBack userPickServiceBack) {
		this.userBasicServiceBack = userBasicServiceBack;
		this.userAssetServiceBack = userAssetServiceBack;
		this.userDetailServiceBack = userDetailServiceBack;
		this.userLifeServiceBack = userLifeServiceBack;
		this.userStatusServiceBack = userStatusServiceBack;
		this.userPickServiceBack = userPickServiceBack;
	}

	@GetMapping("basic")
	@ResponseBody
	public PageInfo<UserBasic> userBasicList(Page<UserBasic> page, @RequestParam String identity, @RequestParam String column, @RequestParam String keyword) {
		logger.info("接收参数：identity=" + identity + ",pageNum=" + page.getPageNum() + ",pageSize=" + page.getPageSize() + ",column=" + column + ",keyword=" + keyword);
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<UserBasic> basics = userBasicServiceBack.selectAllByIdentity(identity, column, "%" + keyword + "%");
		// 遍历list查出所有相对应的Asset和Detail数据
		basics.forEach(user -> {
			UserAsset asset = userAssetServiceBack.selectByPrimaryKey(user.getId());
			UserDetail detail = userDetailServiceBack.selectByPrimaryKey(user.getId());
			if (asset != null) {
				user.setVip(LoverUtil.getDiffOfHours(asset.getVipDeadline()) > 0);
				user.setStar(LoverUtil.getDiffOfHours(asset.getStarDeadline()) > 0);
			}
			if (detail != null) {
				user.setAuthenticated(detail.getCardnumber() != null);
			}
		});
		PageInfo<UserBasic> pageInfo = new PageInfo<>(basics);
		logger.info(JSONObject.toJSON(pageInfo));
		return pageInfo;
	}

	@GetMapping("{type}/{id}")
	@ResponseBody
	public Object userBasic(@PathVariable int id, @PathVariable String type) throws NoSuchFieldException, IllegalAccessException {
		Field declaredField = this.getClass().getDeclaredField("user" + StringUtils.capitalize(type) + "ServiceBack");
		declaredField.setAccessible(true);
		return ((BaseService) declaredField.get(this)).selectByPrimaryKey(id);
	}
	@GetMapping("detail")
	public String userDetail(){
		return "back/user/user_detail";
	}
	@GetMapping("list")
	public String userList(){
		return "back/user/user_list";
	}

	@PutMapping("basic/{id}")
	@ResponseBody
	public boolean updateUser(@PathVariable int id, UserBasic userBasic) {
		userBasic.setId(id);
		return userBasicServiceBack.updateByPrimaryKeySelective(userBasic);
	}
}
