package cn.mldn.util.validator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.mldn.util.resource.ResourceReadUtil;

public class ValidatorUtils {
	private static Logger log = Logger.getLogger(ValidatorUtils.class);
	/**
	 * 实现提交参数的验证，使用指定Action的指定验证规则处理
	 * @param request
	 * @param handlerMethod
	 * @return 所有的验证错误信息保存在Map集合中返回，如果没有错误，则Map集合的长度为0
	 */
	public static Map<String, String> validate(HttpServletRequest request, HandlerMethod handlerMethod) {
		// 通过给定的Controller名称以及要调用的业务方法“rule”一起拼凑出要取出的验证规则，在validation.properties中定义
		String beanName = handlerMethod.getBean().getClass().getSimpleName() ;
		try {
			beanName = beanName.substring(0,beanName.indexOf("$$")) ;
		} catch (Exception e1) {}
		String validationKey = beanName + "." + handlerMethod.getMethod().getName() + ".rules";
//		log.info("【*** preHandle ***】validationValue = " + validationKey);
		
		 
		Map<String,String> errors = new HashMap<String,String>() ;	// 保存所有的验证信息
		try {
			// 现在取得了验证规则的key的信息之后实际上并无法知道该key对应的具体的内容是什么，而内容需要依靠AbstractAction
			// .getValue()取得
			Method getValueMethod = handlerMethod.getBean().getClass().getMethod("getValue", String.class,
					Object[].class);
			try { // 如果现在没有指定的key有可能产生异常，就认为现在没有具体的验证规则出现
					// 通过getValue()方法的Method对象取得对应的验证信息
				String validationValue = (String) getValueMethod.invoke(handlerMethod.getBean(), validationKey, null);
				if (validationValue != null) { // 表示规则现在存在
					// log.info("【*** preHandle ***】validationValue = " + validationValue);
					// 取得全部的提交参数， 需要针对于给定的规则进行拆分控制
					String result[] = validationValue.split("\\|"); // 按照竖线拆分
					for (int x = 0; x < result.length; x++) { // 每一个规则的组成“参数名称:规则类型”
						String temp[] = result[x].split(":");
						String paramName = temp [0];
						String paramRule = temp [1] ;	// 验证规则
						String paramValue = request.getParameter(paramName) ;
						// log.info("【提交参数】paramName = " + paramName + "、paramValue = " + request.getParameter(paramName));
						switch (paramRule) {
							case "string" : {
								if (!ValidateRuleUtil.isString(paramValue)) {	// 该验证没有通过
									String msg = (String) getValueMethod.invoke(handlerMethod.getBean(), "validation.string.msg", null) ;
									errors.put(paramName, msg) ;
								}
								break ;
							} 
							case "int" : {
								if (!ValidateRuleUtil.isInt(paramValue)) {	// 该验证没有通过
									String msg = (String) getValueMethod.invoke(handlerMethod.getBean(), "validation.int.msg", null) ;
									errors.put(paramName, msg) ;
								}
								break ;
							} 
							case "double" : {
								if (!ValidateRuleUtil.isDouble(paramValue)) {	// 该验证没有通过
									String msg = (String) getValueMethod.invoke(handlerMethod.getBean(), "validation.double.msg", null) ;
									errors.put(paramName, msg) ;
								}
								break ;
							} 
							case "date" : {
								if (!ValidateRuleUtil.isDate(paramValue)) {	// 该验证没有通过
									String msg = (String) getValueMethod.invoke(handlerMethod.getBean(), "validation.date.msg", null) ;
									errors.put(paramName, msg) ;
								}
								break ;
							} 
							case "datetime" : {
								if (!ValidateRuleUtil.isDatetime(paramValue)) {	// 该验证没有通过
									String msg = (String) getValueMethod.invoke(handlerMethod.getBean(), "validation.datetime.msg", null) ;
									errors.put(paramName, msg) ;
								}
								break ;
							} 
							case "rand" : {
								if (!ValidateRuleUtil.isRand(request,paramValue)) {	// 该验证没有通过
									String msg = (String) getValueMethod.invoke(handlerMethod.getBean(), "validation.rand.msg", null) ;
									errors.put(paramName, msg) ;
								} 
								break ;
							}
						}
					}
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}
		if (errors.size() == 0) {	// 之前没有错误信息，现在表示我可以对上传文件类型进行验证
			// 需要判断是否当前有上传文件
			MultipartResolver mr = new CommonsMultipartResolver() ;		// 通过它来判断对于上传文件的接收操作
			if (mr.isMultipart(request)) {	// 表示的是当前有上传文件
				// 需要拼凑验证规则使用的key的信息
				String mimeKey = handlerMethod.getBean().getClass().getSimpleName() + "."
						+ handlerMethod.getMethod().getName() + ".mime.rules" ;
				// 取得具体的验证规则的消息
				String mimeValue = ResourceReadUtil.getValue(handlerMethod, mimeKey) ;
				if (mimeValue == null) {	// 没有消息读到，没有设置单独的验证规则
					mimeValue = ResourceReadUtil.getValue(handlerMethod, "mime.rules") ;
				}
				// 进行每一个上传文件的具体验证操作
				String mimeResult [] = mimeValue.split("\\|") ;	// 因为是一组规则，所以需要拆分
				MultipartRequest mreq = (MultipartRequest) request ;	// 处理上传时的request
				Map<String,MultipartFile> fileMap = mreq.getFileMap() ;	// 取得全部的上传文件
				if (fileMap.size() > 0) {	// 现在有上传文件
					// 需要判断每一个文件的类型
					Iterator<Map.Entry<String,MultipartFile>> iter = fileMap.entrySet().iterator() ;
					while (iter.hasNext()) {	// 判断每一个文件的类型
						Map.Entry<String,MultipartFile> me = iter.next() ;
						if (me.getValue().getSize() > 0) {	// 当前的这个上传文件的长度大于0，有上传
							if (!ValidateRuleUtil.isMime(mimeResult, me.getValue().getContentType())) {	// 没有验证通过
								errors.put("file", ResourceReadUtil.getValue(handlerMethod, "validation.mime.msg")) ;
							}
						}
					}
				}
			}
		}
		return errors ;
	}
}







