package com.example.auth.client.factory;

import com.example.auth.client.AuthClient;
import com.example.auth.client.fallback.AuthServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceFallbackFactory implements FallbackFactory<AuthClient> {

	@Override
	public AuthClient create(Throwable throwable) {
		AuthServiceFallbackImpl authServiceFallback = new AuthServiceFallbackImpl();
		authServiceFallback.setCause(throwable);
		return authServiceFallback;
	}
}
