package com.hpe.findlover.filter;

import com.hpe.findlover.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserSessionFilter extends AccessControlFilter {
	private Logger logger = LogManager.getLogger(UserSessionFilter.class);
	@Autowired
	private UserService userService;

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject == null) { // 没有登录
			logger.info("当前Subject为null");
			return false;
		} else {
			if (subject.isRemembered()) {
				logger.info("当前Subject " + subject.getPrincipal() + " Is Remembered");
			}
			HttpSession session = ((HttpServletRequest) request).getSession();
			if (session.getAttribute("user") == null) {
				logger.info("填入User对象数据到Session--");
				session.setAttribute("user", userService.selectByEmail((String) subject.getPrincipal()));
			}
		}
		return super.preHandle(request, response);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}
}
