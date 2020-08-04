package com.example.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtil {

	public static String encryptSHA(String source) {
        return DigestUtils.md5Hex(source == null ? "" : source);
	}

    public static void main(String[] args) {
        String[] cn20 = new String[]{"bgt123&","zcfgs123&"};
        for (String s : cn20) {
            System.out.println(encryptSHA(s));
        }
    }
}
