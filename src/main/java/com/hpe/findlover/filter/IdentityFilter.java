package com.hpe.findlover.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证身份的过滤器，identity取值：user, admin, writer.
 */
public class IdentityFilter extends UserFilter {
	private final Logger logger = LogManager.getLogger(IdentityFilter.class);
	private String identity;

	public IdentityFilter(String identity) {
		this.identity = identity;
		setLoginUrl("user".equals(identity) ? "/login" : "admin".equals(identity) ? "/admin/login" : "/writer/login");
		if (getLoginUrl().equals(DEFAULT_LOGIN_URL)) {
			throw new AuthenticationException("未知的身份：" + identity);
		}
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		logger.info("Session中身份：" + identity + "=" + getSubject(request, response).getSession().getAttribute(identity));
		if (isLoginRequest(request, response)) {
			logger.info("loginRequest!");
			if(getSubject(request, response).getSession().getAttribute(identity) != null){
				try {
					logger.info("redirect to index");
					((HttpServletResponse) response).sendRedirect("index");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}else {
			logger.info("Is not loginRequest!");
			return getSubject(request, response).getSession().getAttribute(identity) != null;
		}
	}
}
