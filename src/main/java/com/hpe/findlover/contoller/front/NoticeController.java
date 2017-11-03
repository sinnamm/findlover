package com.hpe.findlover.contoller.front;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.Notice;
import com.hpe.findlover.model.NoticeUser;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.NoticeService;
import com.hpe.findlover.service.NoticeUserService;
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

    private Logger logger = LogManager.getLogger(NoticeController.class);

    @GetMapping
    public String notice(Model model,HttpServletRequest request){
        UserBasic userBasic = SessionUtils.getSessionAttr(request,"user",UserBasic.class);
        List<Notice> notices = noticeService.selectUnReadNotice(userBasic);
        List<Notice> notices1 = noticeService.selectReadNotice(userBasic);
        model.addAttribute("readCount",notices.size());
        model.addAttribute("unReadCount",notices.size());
        return "front/notice";
    }

    @GetMapping("unReadNotice")
    @ResponseBody
    public PageInfo getUnReadNotice(@RequestParam("pageNum")int pageNum, HttpServletRequest request){
        UserBasic userBasic = SessionUtils.getSessionAttr(request,"user",UserBasic.class);
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
        UserBasic userBasic = SessionUtils.getSessionAttr(request,"user",UserBasic.class);
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
        UserBasic userBasic = SessionUtils.getSessionAttr(request,"user",UserBasic.class);
        NoticeUser noticeUser = noticeUserService.selectOne(new NoticeUser(id,userBasic.getId(),null));
        if (noticeUser==null) {
            noticeUserService.insert(new NoticeUser(id, userBasic.getId(), new Date()));
        }
        Notice notice = noticeService.selectByPrimaryKey(id);
        return notice;
    }
}
