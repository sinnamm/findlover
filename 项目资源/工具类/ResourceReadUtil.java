package cn.mldn.util.resource;

import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;

public class ResourceReadUtil {
	/**
	 * 读取错误页的配置消息
	 * @param handlerMethod
	 * @return
	 */
	public static String getErrorPageValue(HandlerMethod handlerMethod) {
		String pageKey = handlerMethod.getBean().getClass().getSimpleName() + "."
				+ handlerMethod.getMethod().getName() + ".error.page";
		String pageUrl = getValue(handlerMethod,pageKey) ;
		if (pageUrl == null) {
			pageUrl = getValue(handlerMethod,"error.page") ;
		}  
		return pageUrl ; 
	}
	/**
	 * 实现消息的手工配置读取
	 * @param handlerMethod
	 * @param msgKey
	 * @return
	 */
	public static String getValue(HandlerMethod handlerMethod, String msgKey) {
		try {
			Method getValueMethod = handlerMethod.getBean().getClass().getMethod("getValue", String.class,
					Object[].class);
			return getValueMethod.invoke(handlerMethod.getBean(), msgKey, null).toString();
		} catch (Exception e) {
			return null ; 
		} 
	}
}
