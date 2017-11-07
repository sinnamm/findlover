package com.hpe.findlover.interceptor;

import com.hpe.findlover.anno.MyParam;
import com.hpe.findlover.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * 自定义参数封装解析器
 * Controller类中自定义的方法的参数传入前处理
 *
 * @author hgh
 */
@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private Logger logger = LogManager.getLogger(UserHandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(MyParam.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) throws Exception {

        Class<?> parameterType = methodParameter.getParameterType();

        if (parameterType.equals(String.class)) {
            String paramName = methodParameter.getParameterName();
            String paramValue = nativeWebRequest.getParameter(paramName);
            if ("".equals(paramValue) || "-1".equals(paramValue)) {
                return null;
            } else {
                return paramValue;
            }
        }
        if (parameterType.equals(Integer.class)) {
            String paramName = methodParameter.getParameterName();
            String paramValue = nativeWebRequest.getParameter(paramName);
            if ("".equals(paramValue) || "-1".equals(paramValue)) {
                return null;
            } else {
                return Integer.parseInt(paramValue);
            }
        }
        if (parameterType.equals(UserBasic.class)
                || parameterType.equals(UserDetail.class)
                || parameterType.equals(UserLife.class)
                || parameterType.equals(UserStatus.class)
                || parameterType.equals(UserPick.class)) {
            Object userObj = BeanUtils.instantiate(methodParameter.getParameterType());
            Field[] objFields = methodParameter.getParameterType().getDeclaredFields();
            for (Iterator<String> itr = nativeWebRequest.getParameterNames(); itr.hasNext(); ) {
                //前台传来的每一个参数name的名称
                String paramName = itr.next();
                logger.debug("ParamName: " + paramName);
                for (int i = 0; i < objFields.length; i++) {
                    objFields[i].setAccessible(true);
                    if (paramName.equals(objFields[i].getName())) {
                        //前台传来的每一个参数name所对应的value值
                        String paramValue = nativeWebRequest.getParameter(paramName);
                        if (paramValue.equals("-1") || paramValue.equals("")) {
                            continue;
                        }
                        if ("Integer".equals(objFields[i].getType().getSimpleName())) {
                            objFields[i].set(userObj, Integer.parseInt(paramValue));
                        } else if ("String".equals(objFields[i].getType().getSimpleName())) {
                            objFields[i].set(userObj, paramValue);
                        } else if ("Date".equals(objFields[i].getType().getSimpleName())) {
                            objFields[i].set(userObj, Date.valueOf(paramValue));
                        } else if ("Double".equals(objFields[i].getType().getSimpleName())) {
                            objFields[i].set(userObj, Double.valueOf(paramValue));
                        }
                    }
                }
            }
            return userObj;
        }
        return null;
    }
}

