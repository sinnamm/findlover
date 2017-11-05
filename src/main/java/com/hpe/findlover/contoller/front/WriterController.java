package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.Essay;
import com.hpe.findlover.model.Writer;
import com.hpe.findlover.service.EssayService;
import com.hpe.findlover.service.UploadService;
import com.hpe.findlover.service.WriterService;
import com.hpe.findlover.token.CustomToken;
import com.hpe.findlover.util.Identity;
import com.hpe.findlover.util.ShiroHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * 专栏作家控制器
 *
 * @author hgh
 */
@Controller
@RequestMapping("writer")
public class WriterController {
    private Logger logger = LogManager.getLogger(WriterController.class);

    @Autowired
    private UploadService uploadService;
    @Autowired
    private EssayService essayService;
    @Autowired
    private WriterService writerService;


    /**
     * 跳转作者专栏申请文章发表页面
     *
     * @return
     */
    @RequestMapping({"","index"})
    public String writeUI() {
        return "front/writer";
    }

    /**
     * 跳转作者专栏登录页面
     *
     * @return
     */
    @GetMapping("login")
    public String writeLoginUI() {
        return "front/writer_login";
    }

    /**
     * 跳转作者专栏登录页面
     *
     * @return
     */
    @GetMapping("logout")
    public String writeLogoutUI() {
        SecurityUtils.getSubject().logout();
        SecurityUtils.getSubject().getSession().getAttribute("writer");
        return "front/writer_login";
    }

    /**
     * 跳转作者专栏登录操作
     *
     * @return
     */
    @PostMapping("login")
    public String login(HttpServletRequest request, Writer writer, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(writer.getUsername()) || StringUtils.isEmpty(writer.getPassword())) {
            redirectAttributes.addAttribute("message", "用户名或密码不能为空！");
            return "redirect:login";
        }
        CustomToken token = new CustomToken(writer.getUsername(), writer.getPassword(), Identity.WRITER);
        try {
            SecurityUtils.getSubject().login(token);
            if (SecurityUtils.getSubject().isAuthenticated()) {
                ShiroHelper.flushSession();
                HttpSession session = request.getSession();
                session.setAttribute("writer", writerService.selectByUserName(writer.getUsername()));
                return "redirect:index";
            } else {
                return "redirect:login";
            }
        } catch (UnknownAccountException uae) {
            logger.debug("对用户[" + writer.getUsername() + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addAttribute("message", "用户名不存在!");
        } catch (IncorrectCredentialsException ice) {
            logger.debug("对用户[" + writer.getUsername() + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addAttribute("message", "密码不正确!");
        } catch (LockedAccountException ule) {
            logger.debug("对用户[" + writer.getUsername() + "]进行登录验证..验证未通过,用户被锁定");
            redirectAttributes.addAttribute("message", "用户被锁定!");
        }
        return "redirect:login";
    }

    /**
     * 文章保存
     *
     * @param essays
     * @throws Exception
     */
    @PostMapping("upload")
    public Object uploadEssay(HttpSession session, String essays
            , Essay essay, MultipartFile ephoto,Model model) throws Exception {
        Writer writer = (Writer) session.getAttribute("writer");
        logger.debug("writer=" + writer);
        if (essays != null && ephoto != null && writer!=null) {
            logger.debug(essays);
            String filePath = uploadService.uploadFile(essays, "txt");
            String photoPath = uploadService.uploadFile(ephoto);
            essay.setWriterId(writer.getId());
            essay.setFilename(filePath);
            essay.setLikeCount(0);
            essay.setVisitCount(0);
            essay.setStatus(Essay.UNCHECKED_STATUS);
            essay.setPhoto(photoPath);
            //提交文章的同时生成文章并且生成该作家
            // logger.debug(filePath);
            essayService.insert(essay);
            model.addAttribute("msg", essays);
        }
        //return essays;
        return "front/result";
    }

    @RequestMapping("result")
    public String resultUI(String msg, Model model) {
        model.addAttribute("msg", msg);
        return "front/result";
    }


}
