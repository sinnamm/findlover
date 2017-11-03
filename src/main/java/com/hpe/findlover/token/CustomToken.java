package com.hpe.findlover.token;

import com.hpe.findlover.util.Identity;
import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomToken extends UsernamePasswordToken {
	/**
	 * 登录身份：user, admin, writer
 	 */
	private Identity type;

	public CustomToken(String username, String password, Identity type) {
		super(username, password);
		this.type = type;
	}

	public Identity getType() {
		return type;
	}

	public void setType(Identity type) {
		this.type = type;
	}
}
