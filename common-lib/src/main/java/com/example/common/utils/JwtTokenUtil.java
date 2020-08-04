package com.example.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.example.common.bo.UserInfoBo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken 生成的工具类
 * <p>
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户id、创建时间、生成时间）：
 * {"sub":"userid","created":1581910063623,"exp":15819123455}
 * signature的生成算法：
 * HMAC SHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 * @author Leo
 * @date 2020.02.17
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenUtil {

	// 用户ID
	private static final String CLAIM_KEY_USERID = "sub";

	// 创建时间
	private static final String CLAIM_KEY_CREATED = "created";

	// JWT 秘钥
	private String secret;

	// Payload 秘钥
	private String payloadSecret;

	// JWT 过期
	private Long expiration;

	// JWT 刷新间隔
	private int refreshInterval;

	// JWT 的前缀头信息，一般为 'Bearer ' (有空格)
	private String tokenHead;

	/**
	 * 根据负责生成 JWT 的 token
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	/**
	 * 从 token 中获取 JWT 中的负载
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			log.info("JWT格式验证失败:{}", token);
		}
		return claims;
	}

	/**
	 * 生成 token 的过期时间
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	/**
	 * 从 token 中获取登录用户ID
	 */
	public String getUserIdFromToken(String token) {
		String userId;
		try {
			Claims claims = getClaimsFromToken(token);
			AES aes = SecureUtil.aes(payloadSecret.getBytes());
			userId = aes.decryptStr(claims.getSubject(), CharsetUtil.CHARSET_UTF_8);
		} catch (Exception e) {
			userId = null;
		}
		return userId;
	}

	/**
	 * 从 token 中获取 JWT 中的签名
	 */
	public String getSignatureFromToken(String token) {
		String signature = null;
		try {
			signature = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getSignature();
		} catch (Exception e) {
			log.info("JWT格式验证失败:{}", token);
		}
		return signature;
	}

	/**
	 * 验证 token 是否还有效
	 *
	 * @param token      客户端传入的 token
	 * @param userInfoBo 用户信息
	 */
	public boolean validateToken(String token, UserInfoBo userInfoBo) {
		String userId = getUserIdFromToken(token);
		return userId.equals(userInfoBo.getUserId().toString()) && !isTokenExpired(token);
	}

	/**
	 * 判断 token 是否已经失效
	 */
	private boolean isTokenExpired(String token) {
		Date expiredDate = getExpiredDateFromToken(token);
		return expiredDate.before(new Date());
	}

	/**
	 * 从 token 中获取过期时间
	 */
	public Date getExpiredDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	/**
	 * 获取真正的 Token，去掉 tokenHead (一般是 Bearer)
	 *
	 * @param authorization 带 tokenHead 的 token
	 */
	public String getTokenByAuthorization(String authorization) {
		if (StrUtil.isEmpty(tokenHead)) {
			return null;
		}

		String token = authorization.substring(tokenHead.length());
		if (StrUtil.isEmpty(token)) {
			return null;
		}
		return token;
	}

	/**
	 * 根据用户信息生成 token
	 */
	public String generateToken(UserInfoBo userInfoBo) {
		Map<String, Object> claims = new HashMap<>();

		// userId 加密
		AES aes = SecureUtil.aes(payloadSecret.getBytes());
		String userIdHex = aes.encryptHex(userInfoBo.getUserId().toString());
		claims.put(CLAIM_KEY_USERID, userIdHex);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	/**
	 * 当原来的 token 没过期时，用旧的token可以刷新
	 *
	 * @param oldToken token
	 */
	public String refreshToken(String oldToken) {
		if (StrUtil.isEmpty(oldToken)) {
			return null;
		}

		// token 校验不通过
		Claims claims = getClaimsFromToken(oldToken);
		if (claims == null) {
			return null;
		}

		// 如果 token 已经过期，不支持刷新
		if (isTokenExpired(oldToken)) {
			return null;
		}

		// 如果 token 在指定时间内分钟之内刚刷新过，返回原 token
		if (tokenRefreshJustBefore(oldToken, refreshInterval)) {
			return oldToken;
		} else {
			claims.put(CLAIM_KEY_CREATED, new Date());
			return generateToken(claims);
		}
	}

	/**
	 * 当原来的 token 没过期时是可以刷新的
	 *
	 * @param oldToken 带 tokenHead 的 token
	 */
	public String refreshHeadToken(String oldToken) {
		if (StrUtil.isEmpty(oldToken)) {
			return null;
		}

		String token = oldToken.substring(tokenHead.length());
		if (StrUtil.isEmpty(token)) {
			return null;
		}

		// token 校验不通过
		Claims claims = getClaimsFromToken(token);
		if (claims == null) {
			return null;
		}

		// 如果 token 已经过期，不支持刷新
		if (isTokenExpired(token)) {
			return null;
		}

		// 如果 token 在指定时间内分钟之内刚刷新过，返回原 token
		if (tokenRefreshJustBefore(token, refreshInterval)) {
			return token;
		} else {
			claims.put(CLAIM_KEY_CREATED, new Date());
			return generateToken(claims);
		}
	}

	/**
	 * 判断 token 在指定时间内是否刚刚刷新过
	 *
	 * @param token 原 token
	 * @param time  指定时间（秒）
	 */
	private boolean tokenRefreshJustBefore(String token, int time) {
		Claims claims = getClaimsFromToken(token);
		Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
		Date refreshDate = new Date();

		// 刷新时间在创建时间的指定时间内
		if (refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time))) {
			return true;
		}
		return false;
	}
}
