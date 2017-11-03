package com.hpe.findlover.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {
	private static Logger logger = LogManager.getLogger(SessionUtils.class);

	public static <T> T getSessionAttr(String attrName, Class<T> clazz) {
		Object attrValue = SecurityUtils.getSubject().getSession().getAttribute(attrName);
		if (attrValue == null) {
			throw new NullPointerException("Session中" + attrName + "属性为null");
		}
		if(!clazz.isInstance(attrValue)) {
			throw new ClassCastException("session属性类型与指定类型不符！");
		}
		return clazz.cast(attrValue);
	}
}
