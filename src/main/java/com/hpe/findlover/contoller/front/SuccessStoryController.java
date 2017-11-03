package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.SuccessStory;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.SuccessStoryService;
import com.hpe.findlover.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
        if (successStoryService.insert(successStory)){
            return "front/story_view";
        }
        return "front/error";
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

    @GetMapping("/story_view")
    public String successStoryView() {
        return "front/story_view";
    }
}
