package com.hpe.findlover.contoller.front;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpe.findlover.model.*;
import com.hpe.findlover.service.SuccessStoryLikeService;
import com.hpe.findlover.service.SuccessStoryReplyService;
import com.hpe.findlover.service.SuccessStoryService;
import com.hpe.findlover.service.UploadService;
import com.hpe.findlover.util.SessionUtils;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author sinnamm
 * @Date Create in  2017/10/17.
 */
@Controller
@RequestMapping("success_story")
public class SuccessStoryController {
    @Autowired
    private SuccessStoryService successStoryService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private SuccessStoryLikeService successStoryLikeService;
    @Autowired
    private SuccessStoryReplyService successStoryReplyService;
    Logger logger = LoggerFactory.getLogger(SuccessStoryController.class);
    @PostMapping("write_story")
    public String uploadEssay(HttpSession session, String essays, int otherId, String title,
                              MultipartFile ephoto, String tcontent,Model model) throws Exception{
        SuccessStory successStory=new SuccessStory();
        UserBasic user= (UserBasic) session.getAttribute("user");
        successStory.setLeftUser(user.getId());
        successStory.setRightUser(otherId);
        successStory.setTitle(title);
        successStory.setStatus(2);
        successStory.setSuccessTime(new Date());
        successStory.setContent(uploadService.uploadFile(essays,"txt"));
        successStory.setPhoto(uploadService.uploadFile(ephoto));
        successStory.setBrief(tcontent);
        successStory.setLikeCount(0);
        successStory.setReplyCount(0);
        model.addAttribute("msg", essays);
        if (successStoryService.insertStory(successStory)){
            return "front/story_view";
        }
        return "front/error";
    }
    @GetMapping("write_story")
    public String writeStory(){
        return "front/write_story";
    }

    @GetMapping
    public String successStory(Model model){
        PageHelper.startPage(1,8,"success_time desc");
        List<SuccessStory> list= successStoryService.selectAllByStatus();
        model.addAttribute("list",list);
        return"front/success_story";
    }
    @PostMapping("load_more")
    @ResponseBody
    public Object loadMore(int lineSize,int currentPage){
        PageHelper.startPage(currentPage,lineSize,"success_time desc");
        PageInfo<SuccessStory> pageInfo= new PageInfo<>(successStoryService.selectAllByStatus());
        return pageInfo;
    }
    @PostMapping("story_reply/load_more")
    @ResponseBody
    public Object loadMoreReply(int storyId,int currentPage,int lineSize){
        PageHelper.startPage(currentPage,lineSize,"reply_time desc");
        PageInfo<SuccessStoryReply> pageInfo=new PageInfo<>(successStoryReplyService.selectByStoryId(storyId));
        return pageInfo;
    }
    @GetMapping("success_story_info/{id}")
    public String successStoryInfo(@PathVariable int id,HttpSession session,Model model) throws Exception{
        logger.error("访问success_story_info Controller");
        SuccessStory successStory=successStoryService.selectByPrimaryKey(id);
        String filename = successStory.getContent();
        byte[] bytes = uploadService.downloadFile(filename);
        String content = new String(bytes, "utf-8");
        UserBasic userBasic= (UserBasic) session.getAttribute("user");
        Boolean like=successStoryLikeService.selectByStoryIdAndUserId(id,userBasic.getId());
        int likeCount=successStoryLikeService.selectCountByStoryId(id);
        PageHelper.startPage(1,5,"reply_time desc");
        PageInfo<SuccessStoryReply> pageInfo=new PageInfo<>(successStoryReplyService.selectByStoryId(id));
        model.addAttribute("successStory",successStory);
        model.addAttribute("content", content);
        model.addAttribute("like",like?"true":"false");
        model.addAttribute("likeCount",likeCount);
        model.addAttribute("reply",pageInfo);
        return "front/success_story_info";
    }

    @GetMapping("story_view")
    public String successStoryView() {
        return "front/story_view";
    }
    @PostMapping("reply")
    @ResponseBody
    public String reply(String content, int storyId,HttpSession session){
        UserBasic userBasic= SessionUtils.getSessionAttr("user",UserBasic.class);
        SuccessStoryReply successStoryReply=new SuccessStoryReply();
        successStoryReply.setContent(content);
        successStoryReply.setSsId(storyId);
        successStoryReply.setUserId(userBasic.getId());
        successStoryReply.setReplyTime(new Date());
        if (successStoryReplyService.insert(successStoryReply)){
            return "true";
        }
        return "false";
    }
    @PostMapping("like")
    @ResponseBody
    public String like(int storyId,HttpSession session){
        UserBasic userBasic= SessionUtils.getSessionAttr("user",UserBasic.class);
        SuccessStoryLike successStoryLike=new SuccessStoryLike();
        successStoryLike.setLikeTime(new Date());
        successStoryLike.setSuccessStoryId(storyId);
        successStoryLike.setUserId(userBasic.getId());
        if (successStoryLikeService.insert(successStoryLike)){
            return "true";
        }
        return "false";
    }

    @GetMapping("confirmSuccessStory/{id}")
    public String confirm(@PathVariable int id, Model model){
        logger.info("需要确认的id:"+id);
        SuccessStory successStory = successStoryService.selectByPrimaryKey(id);
        String filename = successStory.getContent();
        byte[] bytes = uploadService.downloadFile(filename);
        try {
            String content = new String(bytes, "utf-8");
            model.addAttribute("essay", content);
            model.addAttribute("successStory", successStory);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
        return "front/confirm_success_story";
    }

    @GetMapping("pass")
    @ResponseBody
    public String pass(SuccessStory successStory){
        logger.info("通过的成功故事："+successStory);
        UserBasic userBasic = SessionUtils.getSessionAttr("user",UserBasic.class);
        if (successStoryService.checkUser(userBasic.getId())){
            boolean result = successStoryService.updateByPrimaryKeySelective(successStory);
            if (result){
                return "success";
            }else {
                return "error";
            }
        }else {
            return "error";
        }

    }

}
