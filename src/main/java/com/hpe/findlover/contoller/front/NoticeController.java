package com.hpe.findlover.contoller.front;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Notice;
import com.hpe.findlover.model.NoticeUser;
import com.hpe.findlover.model.UserBasic;
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
import java.util.Date;
import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/11/2.
 */
@Controller
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeUserService noticeUserService;
    @Autowired
    private FollowService followService;
    @Autowired
    private LetterService letterService;
    @Autowired
    private VisitTraceService visitTraceService;
    @Autowired
    private UserService userService;

    private Logger logger = LogManager.getLogger(NoticeController.class);

    @GetMapping
    public String notice(Model model,HttpServletRequest request){
        UserBasic userBasic = SessionUtils.getSessionAttr("user",UserBasic.class);
        //性取向对应的新注册用户
        PageHelper.startPage(1,4,"reg_time desc");
        List<UserBasic> userBasics = userService.select(new UserBasic(userBasic.getSexual()));
        for(UserBasic userBasicl:userBasics){
            userBasicl.setAge(LoverUtil.getAge(userBasicl.getBirthday()));
        }
        int userId = userBasic.getId();
        List<Notice> notices = noticeService.selectUnReadNotice(userBasic);
        List<Notice> notices1 = noticeService.selectReadNotice(userBasic);
        model.addAttribute("letterCount", letterService.selectUnreadCount(userId));
        model.addAttribute("followCount", followService.selectFollowCount(userId));
        model.addAttribute("visitTraceCount",visitTraceService.selectUnreadCount(userId));
        model.addAttribute("noticeCount", notices.size());
        //广告位
        model.addAttribute("users",userBasics);
        return "front/notice";
    }

    @GetMapping("unReadNotice")
    @ResponseBody
    public PageInfo getUnReadNotice(@RequestParam("pageNum")int pageNum, HttpServletRequest request){
        UserBasic userBasic = SessionUtils.getSessionAttr("user",UserBasic.class);
        logger.info("未读通知页数:"+pageNum);
        PageHelper.startPage(pageNum,10,"pub_time desc");
        List<Notice> notices = noticeService.selectUnReadNotice(userBasic);
        notices.forEach(logger::info);
        PageInfo pageInfo = new PageInfo(notices);
        return pageInfo;
    }

    @GetMapping("readNotice")
    @ResponseBody
    public PageInfo getReadNotice(@RequestParam("pageNum")int pageNum, HttpServletRequest request){
        UserBasic userBasic = SessionUtils.getSessionAttr("user",UserBasic.class);
        logger.info("已读通知页数:"+pageNum);
        PageHelper.startPage(pageNum,10,"pub_time desc");
        List<Notice> noticeUsers = noticeService.selectReadNotice(userBasic);
        noticeUsers.forEach(logger::info);
        PageInfo pageInfo = new PageInfo(noticeUsers);
        return pageInfo;
    }

    @GetMapping("read/{id}")
    @ResponseBody
    public Notice read(@PathVariable("id")int id,HttpServletRequest request){
        logger.info("读取数据："+id);
        UserBasic userBasic = SessionUtils.getSessionAttr("user",UserBasic.class);
        NoticeUser noticeUser = noticeUserService.selectOne(new NoticeUser(id,userBasic.getId(),null));
        if (noticeUser==null) {
            noticeUserService.insert(new NoticeUser(id, userBasic.getId(), new Date()));
        }
        Notice notice = noticeService.selectByPrimaryKey(id);
        return notice;
    }
}
