package com.hpe.findlover.util;

import com.hpe.findlover.model.UserBasic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {
	private static Logger logger = LogManager.getLogger(SessionUtils.class);

	public static <T> T getSessionAttr(HttpServletRequest request, String attrName, Class<T> clazz) {
		Object attrValue = request.getSession().getAttribute(attrName);
		if (attrValue == null) {
			logger.error("Session中" + attrName + "属性为null");
			return null;
		}
		if(!clazz.isInstance(attrValue)) {
			throw new ClassCastException("session属性类型与指定类型不符！");
		}
		return clazz.cast(attrValue);
	}
}
