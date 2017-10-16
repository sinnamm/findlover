package cn.mldn.util.validate;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.util.resource.ResourceReadUtil;
import cn.mldn.util.validator.ValidatorUtils;
public class ValidationInterceptor implements HandlerInterceptor {
	Logger log = Logger.getLogger(ValidationInterceptor.class) ;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean flag = true ;	// 默认放行
		try {
			// 需要取得HandlerMethod对象，这样可以取得相关的Action信息
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 表示具体的验证处理操作，所有的错误信息通过Map返回
			Map<String, String> errors = ValidatorUtils.validate(request, handlerMethod);
			if (errors.size() > 0) { // 有错
				request.setAttribute("errors", errors); // 保存在Request属性范围之中
				flag = false; // 表示现在有错误，无法向下执行
				request.getRequestDispatcher(ResourceReadUtil.getErrorPageValue(handlerMethod)).forward(request,
						response);
			} else { // 没有错
				return true;
			}
		} catch (Exception e) {
		}
		return flag;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// log.info("【*** postHandle ***】" + handler.getClass() + "、modelAndView = " + modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// log.info("【*** afterCompletion ***】" + handler.getClass());
	}

}
