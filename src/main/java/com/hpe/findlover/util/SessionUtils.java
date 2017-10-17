package com.hpe.findlover.util;

import com.hpe.findlover.model.UserBasic;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {
	public static <T> T getSessionAttr(HttpServletRequest request,String attrName,Class<T> clazz){
		Object attrValue = request.getSession().getAttribute("attrName");
		if(attrValue == null)
			return null;
		if(!clazz.isInstance(attrValue))
			throw new ClassCastException("session属性类型与指定类型不符！");
		return clazz.cast(attrValue);
	}
}
