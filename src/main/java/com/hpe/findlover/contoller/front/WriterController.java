package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.UserBasic;
import com.hpe.findlover.model.Writer;
import com.hpe.findlover.service.UploadService;
import com.hpe.findlover.service.WriterService;
import com.hpe.findlover.util.MD5Code;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * 专栏作家控制器
 * @author hgh
 */
@Controller
@RequestMapping("writer")
public class WriterController {
    private Logger logger = LogManager.getLogger(WriterController.class);

    @Autowired
    private UploadService uploadService;
    @Autowired
    private WriterService writerService;


    /**
     * 跳转作者专栏申请文章发表页面
     * @return
     */
    @RequestMapping("essay")
    public String writeUI() {
        return "front/writer";
    }

    /**
     * 跳转作者专栏登录页面
     * @return
     */
    @GetMapping("login")
    public String writeLoginUI() {
        return "front/writer_login";
    }
    /**
     * 跳转作者专栏登录操作
     * @return
     */
    @PostMapping("login")
    public String login(HttpServletRequest request, Writer writer, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(writer.getUsername()) || StringUtils.isEmpty(writer.getPassword())) {
            redirectAttributes.addAttribute("message", "专栏作家名或密码不能为空！");
            return "redirect:writer/login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(writer.getUsername(), new MD5Code().getMD5ofStr(writer.getPassword()));
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException uae) {
            logger.error("对专栏作家[" + writer.getUsername() + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addAttribute("message", "专栏作家名不存在");
        } catch (IncorrectCredentialsException ice) {
            logger.error("对专栏作家[" + writer.getUsername() + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addAttribute("message", "密码不正确");
        } catch (LockedAccountException ule){
            logger.error("对专栏作家[" + writer.getUsername() + "]进行登录验证..验证未通过,专栏作家被锁定");
            redirectAttributes.addAttribute("message", "专栏作家被锁定");
        }catch (DisabledAccountException dae){
            logger.error("对专栏作家[" + writer.getUsername() + "]进行登录验证..验证未通过,专栏作家未激活");
            redirectAttributes.addAttribute("message", "专栏作家未激活");
        }
        if (SecurityUtils.getSubject().isAuthenticated()) {
            HttpSession session = request.getSession();
            Enumeration<String> attributeNames = session.getAttributeNames();
            while(attributeNames.hasMoreElements()){
                String name = attributeNames.nextElement();
                logger.info(name+"="+session.getAttribute(name));
            }
            return "redirect:writer/essay";
        }else{
            return "redirect:writer/login";
        }
    }

    /**
     * 文章保存
     * @param essays
     * @throws Exception
     */
    @RequestMapping("upload")
    public Object uploadEssay(String essays,String pseudonym,String title, Model model) throws Exception {
        if(essays!=null) {
            logger.debug(essays);
            String filePath = uploadService.uploadFile(essays, "txt");
            //提交文章的同时生成文章并且生成该作家
            logger.debug(filePath);
            boolean result = writerService.insertWriterAndEssay(filePath, pseudonym, title);
            model.addAttribute("msg", essays);
        }
        //return essays;
        return "front/result";
    }


}
