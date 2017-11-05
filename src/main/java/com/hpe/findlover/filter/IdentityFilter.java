package com.hpe.findlover.filter;

import com.hpe.findlover.util.Identity;
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
	private Identity identity;

	public IdentityFilter(Identity identity) {
		this.identity = identity;
		if(Identity.USER.equals(identity)){
			setLoginUrl("/login");
		} else if (Identity.ADMIN.equals(identity)) {
			setLoginUrl("/admin/login");
		}else if(Identity.WRITER.equals(identity)){
			setLoginUrl("/writer/login");
		}
		if (getLoginUrl().equals(DEFAULT_LOGIN_URL)) {
			throw new AuthenticationException("未知的身份：" + identity.getValue());
		}
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginRequest(request, response)) {
			if(getSubject(request, response).getSession().getAttribute(identity.getValue()) != null){
				try {
					((HttpServletResponse) response).sendRedirect("index");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}else {
			return getSubject(request, response).getSession().getAttribute(identity.getValue()) != null;
		}
	}
}
