package com.hpe.findlover.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Configurable
public class ResourceReader {
	@Autowired
	private static MessageSource messageSource;

	public static String getValue(String key,Object...args){
		return messageSource.getMessage(key, args, Locale.getDefault());
	}
}
