package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.SuccessStory;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.SuccessStoryService;
import com.hpe.findlover.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

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
    Logger logger = LoggerFactory.getLogger(SuccessStoryController.class);
    @PostMapping("upload_essay")
    @ResponseBody
    public String uploadEssay(HttpSession session, String essays, int otherId, String title){
//        0：下架，1：审核通过，2：待右手审核，3：待管理员审核
        logger.error("SuccessStoryController#################uploadEssay");
        SuccessStory successStory=new SuccessStory();
        UserBasic user= (UserBasic) session.getAttribute("user");
        successStory.setLeftUser(user.getId());
        successStory.setRightUser(otherId);
        successStory.setTitle(title);
        successStory.setStatus(2);
        successStory.setSuccessTime(new Date());
        successStory.setContent(uploadService.uploadFile(essays,"txt"));
        successStory.setLikeCount(0);
        successStory.setReplyCount(0);
        if (successStoryService.insert(successStory)){
            return "success";
        }
        return "error";
    }
    @GetMapping("write_story")
    public String writeStory(){
        return "front/write_story";
    }
    @GetMapping
    public String successStory(){
        return "front/success_story";
    }

    @GetMapping("/success_story_info")
    public String successStoryInfo(){
        return "front/success_story_info";
    }
}
