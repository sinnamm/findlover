package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.*;
import com.hpe.findlover.service.BaseService;
import com.hpe.findlover.service.*;
import com.hpe.findlover.util.LoverUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

/**
 * @author Gss
 */
@Controller
@RequiresRoles("user")
@RequestMapping("admin/user")
public class UserControllerBack {
	private Logger logger = LogManager.getLogger(UserControllerBack.class);
	private final UserService userBasicService;
	private final UserAssetService userAssetService;
	private final UserDetailService userDetailService;
	private final UserLifeService userLifeService;
	private final UserStatusService userStatusService;
	private final UserPickService userPickService;
	private final LabelService labelService;
	private final FollowService followService;
	private final VisitTraceService visitTraceService;

	@Autowired
	public UserControllerBack(UserService userBasicService, UserAssetService userAssetService, UserDetailService userDetailService, UserLifeService userLifeService, UserStatusService userStatusService, UserPickService userPickService, LabelService labelService,FollowService followService,VisitTraceService visitTraceService) {
		this.userBasicService = userBasicService;
		this.userAssetService = userAssetService;
		this.userDetailService = userDetailService;
		this.userLifeService = userLifeService;
		this.userStatusService = userStatusService;
		this.userPickService = userPickService;
		this.labelService = labelService;
		this.followService = followService;
		this.visitTraceService = visitTraceService;
	}

	@GetMapping("basic")
	@ResponseBody
	public PageInfo<UserBasic> userBasicList(Page<UserBasic> page, @RequestParam String identity, @RequestParam String column, @RequestParam String keyword) {
		logger.info("接收参数：identity=" + identity + ",pageNum=" + page.getPageNum() + ",pageSize=" + page.getPageSize() + ",column=" + column + ",keyword=" + keyword);
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<UserBasic> basics = userBasicService.selectAllByIdentity(identity, column, "%" + keyword + "%");
		// 遍历list查出所有相对应的Asset和Detail数据
		userBasicService.userAttrHandler(basics);
		PageInfo<UserBasic> pageInfo = new PageInfo<>(basics);
		logger.info(JSONObject.toJSON(pageInfo));
		return pageInfo;
	}

	@GetMapping("{type}/{id}")
	@ResponseBody
	@Cacheable(value = "user-cache", key = "#type+'-'+#id")
	public Object userBasic(@PathVariable int id, @PathVariable String type) throws NoSuchFieldException, IllegalAccessException {
		logger.debug("获取ID为" + id + "的User" + StringUtils.capitalize(type) + "数据...");
		Field declaredField = this.getClass().getDeclaredField("user" + StringUtils.capitalize(type) + "Service");
		declaredField.setAccessible(true);
		return ((BaseService) declaredField.get(this)).selectByPrimaryKey(id);
	}

	@GetMapping("/follower")
	@ResponseBody
	public PageInfo follower(Page<UserBasic> page,@RequestParam("id") int id){
		logger.info("关注我的人接收参数：pageNum=" + page.getPageNum() + ",pageSize=" + page.getPageSize());
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		List<Follow> followers = followService.selectFollower(id);
		followers.forEach(logger::info);
		PageInfo pageInfo = new PageInfo(followers);
		return  pageInfo;
	}

	@GetMapping("/follow")
	@ResponseBody
	public PageInfo follow(Page<UserBasic> page,@RequestParam("id") int id){
		logger.info("我关注的人接收参数：pageNum=" + page.getPageNum() + ",pageSize=" + page.getPageSize());
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		List<Follow> followers = followService.selectFollow(id);
		followers.forEach(logger::info);
		PageInfo pageInfo = new PageInfo(followers);
		return  pageInfo;
	}

	@GetMapping("/trace")
	@ResponseBody
	public PageInfo trace(Page<UserBasic> page,@RequestParam("id") int id){
		logger.info("我看过的人接收参数：pageNum=" + page.getPageNum() + ",pageSize=" + page.getPageSize());
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		List<VisitTrace> followers = visitTraceService.selectVisitTrace(id);
		followers.forEach(logger::info);
		PageInfo pageInfo = new PageInfo(followers);
		return  pageInfo;
	}

	@GetMapping("/tracer")
	@ResponseBody
	public PageInfo tracer(Page<UserBasic> page,@RequestParam("id") int id){
		logger.info("看过我的人接收参数：pageNum=" + page.getPageNum() + ",pageSize=" + page.getPageSize());
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		List<VisitTrace> followers = visitTraceService.selectVisitTracer(id);
		followers.forEach(logger::info);
		PageInfo pageInfo = new PageInfo(followers);
		return  pageInfo;
	}

	@GetMapping("details/{id}")
	public String userDetail(@ModelAttribute @PathVariable int id) {
		return "back/user/user_detail";
	}

	@GetMapping("detail")
	public String userDetailPre() {
		return "back/user/user_detail_pre";
	}

	@GetMapping("list")
	public String userList() {
		return "back/user/user_list";
	}


	@PutMapping("basic/{id}")
	@ResponseBody
	@CachePut(value = "user-cache", key = "'basic-'+#id")
	public boolean updateUser(@PathVariable int id, UserBasic userBasic) {
		userBasic.setId(id);
		return userBasicService.updateByPrimaryKeySelective(userBasic);
	}

	@PostMapping("label")
	@ResponseBody
	public int addLabel(Label label) {
		return labelService.insertUseGeneratedKeys(label) > 0 ? label.getId() : 0;
	}

	@GetMapping("label")
	public String labelList(Model model) {
		model.addAttribute("labels", labelService.selectAll());
		return "back/user/label";
	}

	@PostMapping("label/exists")
	@ResponseBody
	public boolean selectLabel(@RequestParam String name) {
		Label label = new Label();
		label.setName(name);
		boolean result = labelService.selectOne(label) != null;
		logger.debug("名称为“" + name + "”的标签是否存在：" + result);
		return result;
	}

	@DeleteMapping("label/{id}")
	@ResponseBody
	public boolean deleteLabel(@PathVariable int id) {
		return labelService.deleteByPrimaryKey(id) > 0;
	}

	@PutMapping("label/{id}")
	@ResponseBody
	public boolean updateLabel(@PathVariable int id, Label label) {
		label.setId(id);
		return labelService.updateByPrimaryKey(label);
	}

	@RequestMapping("check_id")
	@ResponseBody
	public String checkid(@RequestParam("userId")int userId){
		logger.info("验证用户id");
		UserBasic user= userBasicService.selectByPrimaryKey(userId);
		 if(user!=null){
			return "{\"ok\":\"\"}";
		}else {
			return "{\"error\":\"该id不存在！\"}";
		}
	}
}
