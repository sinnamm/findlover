package com.hpe.findlover.contoller.back;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Admin;
import com.hpe.findlover.model.Complain;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.ComplainService;
import com.hpe.findlover.service.UserService;
import com.hpe.findlover.util.SessionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 投诉控制器
 *
 * @author hgh
 */
@Controller
@RequiresRoles("complain")
@RequestMapping("admin/complain")
public class ComplainControllerBack {
    private Logger logger = LogManager.getLogger(ComplainControllerBack.class);
    @Autowired
    private ComplainService complainService;
    @Autowired
    private UserService userService;

    @GetMapping("list")
    @RequiresPermissions("complain:select")
    public String userList() {
        return "back/complain/complain_list";
    }

    /**
     * 分页搜索
     *
     * @param page     分页对象
     * @param identity 激活或者锁定
     * @param column   按列查询
     * @param keyword  按关键字查询
     * @return
     */
    @GetMapping("info")
    @RequiresPermissions("complain:select")
    @ResponseBody
    public Object complainList(Page<Complain> page, @RequestParam String identity, @RequestParam String column, @RequestParam String keyword) {
        logger.info("接收参数：pageNum=" + page.getPageNum()
                + ",pageSize=" + page.getPageSize() + ",column=" + column + ",keyword=" + keyword);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());

        List<Complain> complains = complainService.selectAllByIdentity(identity, column, "%" + keyword + "%");
        PageInfo<Complain> pageInfo = new PageInfo<>(complains);
        logger.info(JSONObject.toJSON(pageInfo));
        return pageInfo;
    }

    /**
     * 跳转投诉详情界面前读取出投诉数据
     * @param id
     * @param model
     * @return
     */
    @GetMapping("detail/{id}")
    @RequiresPermissions("complain:select")
    public Object complainDetail(@PathVariable int id, Model model) {
        Complain complain = complainService.selectByPrimaryKey(id);
        model.addAttribute("complain", complain);
        return "back/complain/complain_detail";
    }


    /**
     * 修改投诉审核状态
     *
     * @param id    投诉ID
     * @param complain 投诉对象
     * @return
     */
    @PutMapping("info/{id}")
    @RequiresPermissions("complain:authenticate")
    @ResponseBody
    public Object complainList(@PathVariable("id") int id, Complain complain) {
        Admin admin = SessionUtils.getSessionAttr("admin",Admin.class);
        complain.setAdminId(admin.getId());
        complain.setId(id);
        logger.debug(complain);
        return complainService.updateByPrimaryKeySelective(complain);
    }

    /**
     * 封杀用户
     *
     * @param comId    投诉ID
     * @param complain 投诉对象
     * @return
     */
    @PutMapping("seal/{id}")
    @RequiresPermissions("complain:authenticate")
    @ResponseBody
    public Object sealComObj(@PathVariable("id") int comId, Complain complain) {
        Admin admin = SessionUtils.getSessionAttr("admin",Admin.class);
        complain.setAdminId(admin.getId());
        return complainService.updateUserStatusAndComplainStatus(comId,complain);
    }


}
