package com.example.gateway;
import com.example.common.bo.UserInfoBo;
import com.example.common.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import java.io.File;
import java.util.regex.Pattern;

@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
class GatewayApplicationTests {

	@Test
	void contextLoads() {
//		UserInfoBo userInfoBo = UserInfoBo.builder()
//				.userId(123L)
//				.build();
//		String token = JwtTokenUtil.generateToken(userInfoBo);
//		log.info("token:{}", token);

	}

	public static void main(String[] args) {

		UrlPathHelper urlPathHelper = new UrlPathHelper();
//		urlPathHelper.getContextPath();

//		boolean isMatch = Pattern.matches("/role", "/role");
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		antPathMatcher.setCachePatterns(true);
		antPathMatcher.setCaseSensitive(true);
		antPathMatcher.setTrimTokens(true);
		antPathMatcher.setPathSeparator("/");
		boolean a = antPathMatcher.match("*/supplier-api/*/userInfo/user", "/supplier-api/v1/userInfo/user");
		System.out.println(a);
	}
}
