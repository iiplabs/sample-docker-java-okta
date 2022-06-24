package com.iiplabs.dale.web.utils;

import org.apache.commons.codec.binary.Base64;

public final class StringUtil {
	
	public static String fromBase64(String base64) throws IllegalArgumentException {
		return new String(Base64.decodeBase64(base64.getBytes()));
	}

	public static String toBase64(String v) {
		return new String(Base64.encodeBase64(v.getBytes()));
	}

	private StringUtil() {
		throw new AssertionError();
	}

}
