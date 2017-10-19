package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.UserAsset;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.UserDetail;
import com.hpe.findlover.service.back.UserAssetServiceBack;
import com.hpe.findlover.service.back.UserBasicServiceBack;
import com.hpe.findlover.service.back.UserDetailServiceBack;
import com.hpe.findlover.service.front.UserDetailService;
import com.hpe.findlover.util.LoverUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("admin/user")
public class UserControllerBack {
	private Logger logger = LogManager.getLogger(UserControllerBack.class);
	@Autowired
	private UserBasicServiceBack userBasicServiceBack;
	@Autowired
	private UserAssetServiceBack userAssetServiceBack;
	@Autowired
	private UserDetailServiceBack userDetailServiceBack;

	@GetMapping
	@ResponseBody
	public PageInfo<UserBasic> userBasicList(Page<UserBasic> page,@RequestParam String identity) {
		logger.info("接收参数：identity="+identity+",pageNum="+page.getPageNum()+",pageSize="+page.getPageSize());
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<UserBasic> basics = userBasicServiceBack.selectAllByIdentity(identity);
		// 遍历list查出所有相对应的Asset和Detail数据
		basics.forEach(user -> {
			UserAsset asset = userAssetServiceBack.selectByPrimaryKey(user.getId());
			UserDetail detail = userDetailServiceBack.selectByPrimaryKey(user.getId());
			try {
				if (asset != null) {
					user.setVip(LoverUtil.getTime(asset.getVipDeadline()) > 0);
					user.setStar(LoverUtil.getTime(asset.getStarDeadline()) > 0);
				}
				if (detail != null) {
					user.setAuthenticated(detail.getCardnumber() != null);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		PageInfo<UserBasic> pageInfo = new PageInfo<>(basics);
		logger.info(JSONObject.toJSON(pageInfo));
		return pageInfo;
	}
}
