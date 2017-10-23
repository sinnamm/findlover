package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.LetterUser;
import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.service.front.LetterService;
import com.hpe.findlover.service.front.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author YYF;
 */
@Controller
@RequestMapping("letter")
public class LetterController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LetterService letterService;
    @Autowired
    private UserService userService;
    @GetMapping
    public String letter(HttpSession session, Model model)throws Exception{
        UserBasic userBasic=(UserBasic) session.getAttribute("user");
        List<LetterUser> list=letterService.selectOther(userBasic.getId());
        model.addAttribute("list",list);
        return "front/letter";
    }
}
