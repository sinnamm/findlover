package com.hpe.findlover.util;

/**
 * @author Gss
 */

public enum Identity {
	/**
	 * 自定义Token的type
	 */
	USER("user"), ADMIN("admin"), WRITER("writer");
	private String value;

	Identity(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
