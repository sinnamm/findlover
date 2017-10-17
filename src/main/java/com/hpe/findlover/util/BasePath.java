package com.hpe.findlover.util;

import javax.servlet.http.HttpServletRequest;

public class BasePath {
	public static String getBasePath(HttpServletRequest request){
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path ;
		return basePath;
	}
}
