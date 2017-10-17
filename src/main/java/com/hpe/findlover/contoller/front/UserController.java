package com.hpe.findlover.contoller.front;

import com.hpe.findlover.model.UserBasic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author sinnamm
 * @Date 2017-10-16
 * @Description:
 */
@Controller
public class UserController {
	private Logger logger = LogManager.getLogger(UserController.class);

	@GetMapping("index")
	public String index() {
		return "front/index";
	}

	@GetMapping("login")
	public String login() {
		return "front/login";
	}

	@PostMapping("login")
	public String login(HttpServletRequest request,UserBasic user, RedirectAttributes redirectAttributes) {
		if (StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getPassword())) {
			redirectAttributes.addAttribute("message", "用户名或密码不能为空！");
			return "redirect:login";
		}
		UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
		try {
			SecurityUtils.getSubject().login(token);
		} catch (UnknownAccountException uae) {
			logger.error("对用户[" + user.getEmail() + "]进行登录验证..验证未通过,未知账户");
			redirectAttributes.addAttribute("message", "用户名不存在");
		} catch (IncorrectCredentialsException ice) {
			logger.error("对用户[" + user.getEmail() + "]进行登录验证..验证未通过,错误的凭证");
			redirectAttributes.addAttribute("message", "密码不正确");
		}
		if (SecurityUtils.getSubject().isAuthenticated()) {
			HttpSession session = request.getSession();
			Enumeration<String> attributeNames = session.getAttributeNames();
			while(attributeNames.hasMoreElements()){
				String name = attributeNames.nextElement();
				logger.info(name+"="+session.getAttribute(name));
			}
			return "redirect:index";
		}
		else
			return "redirect:login";
	}

	@GetMapping("search")
	public String search() {
		return "front/search";
	}

    @GetMapping("vip")
    public String vip(){
        return "front/vip";
    }
    @GetMapping("success_story")
    public String success_story(){
        return "front/success_story";
    }
    @GetMapping("otherSays")
    public String otherSays(){
        return "front/otherSays";
    }
    @GetMapping("letter")
    public String letter(){
        return "front/letter";
    }
    @GetMapping("care")
    public String care(){
        return "front/care";
    }
    @GetMapping("visiteTrace")
    public String visiteTrace(){
        return "front/visiteTrace";
    }
    @GetMapping("notice")
    public String notice(){
        return "front/notice";
    }
    @GetMapping("user_center")
    public String user_center(){
        return "front/user_center";
    }

}