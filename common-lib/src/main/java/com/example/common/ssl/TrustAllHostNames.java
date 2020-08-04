package com.example.common.ssl;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 信任所有 host name
 *
 * @author Leo
 * @date 2020.02.28
 */
public class TrustAllHostNames implements HostnameVerifier {
	public static final TrustAllHostNames INSTANCE = new TrustAllHostNames();

	@Override
	public boolean verify(String s, SSLSession sslSession) {
		return true;
	}
}
