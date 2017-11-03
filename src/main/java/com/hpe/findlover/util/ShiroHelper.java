package com.hpe.findlover.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


public class ShiroHelper {
	public static void flushSession() {
		Session session = SecurityUtils.getSubject().getSession();
		session.removeAttribute("user");
		session.removeAttribute("admin");
		session.removeAttribute("writer");
	}
}
