package com.example.gateway.config;

import com.example.common.config.OkHttpProps;
import com.example.common.interceptor.OkHttpLogInterceptor;
import com.example.common.logger.OkHttpSlf4jLogger;
import com.example.common.ssl.DisableValidationTrustManager;
import com.example.common.ssl.TrustAllHostNames;
import com.example.common.utils.Holder;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * 配置 Feign
 *
 * @author Leo
 * @date 2020.02.17
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureAfter(FeignAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@Import(value = {OkHttpProps.class})
@Slf4j
public class FeignOkHttpConfig {

	@Autowired
	private OkHttpProps okHttpProps;

	/**
	 * 自定义反序列化解析器
	 *
	 * @return
	 */
	@Bean
	public Decoder feignDecoder() {

		ObjectFactory<HttpMessageConverters> messageConverters = () -> {
			HttpMessageConverters converters = new HttpMessageConverters();
			return converters;
		};
		return new SpringDecoder(messageConverters);
	}

	@Bean
	@ConditionalOnMissingBean({Client.class})
	public Client feignClient(OkHttpClient client) {
		return new feign.okhttp.OkHttpClient(client);
	}

	/**
	 * 日志拦截器
	 *
	 * @return
	 */
	public OkHttpLogInterceptor loggingInterceptor() {
		OkHttpLogInterceptor interceptor = new OkHttpLogInterceptor(new OkHttpSlf4jLogger());
		interceptor.setLevel(okHttpProps.getLevel());
		return interceptor;
	}

	/**
	 * 配置 OkHttp3
	 *
	 * @return
	 */
	@Bean
	public OkHttpClient okHttpClient() {
		int maxTotalConnections = okHttpProps.getMaxConnections();
		long timeToLive = okHttpProps.getTimeToLive();
		TimeUnit ttlUnit = okHttpProps.getTimeUnit();
		ConnectionPool connectionPool = new ConnectionPool(maxTotalConnections, timeToLive, ttlUnit);

		return createBuilder(okHttpProps.isDisableSslValidation())
				// 设置连接超时
				.connectTimeout(okHttpProps.getConnectionTimeout(), ttlUnit)
				// 设置读超时
				.readTimeout(okHttpProps.getReadTimeout(), ttlUnit)
				// 设置写超时
				.writeTimeout(okHttpProps.getWriteTimeout(), ttlUnit)
				// 是否支持重定向
				.followRedirects(okHttpProps.isFollowRedirects())
				// 错误重连
				.retryOnConnectionFailure(okHttpProps.isRetryOnConnectionFailure())
				// 连接池
				.connectionPool(connectionPool)
				// 拦截器
				.addInterceptor(loggingInterceptor())
				.build();
	}


	/**
	 * 构建 SSL
	 *
	 * @param disableSslValidation
	 * @return
	 */
	private OkHttpClient.Builder createBuilder(boolean disableSslValidation) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		if (disableSslValidation) {
			try {
				X509TrustManager disabledTrustManager = DisableValidationTrustManager.INSTANCE;
				TrustManager[] trustManagers = new TrustManager[]{disabledTrustManager};
				SSLContext sslContext = SSLContext.getInstance("SSL");
				sslContext.init(null, trustManagers, Holder.SECURE_RANDOM);
				SSLSocketFactory disabledSslSocketFactory = sslContext.getSocketFactory();
				builder.sslSocketFactory(disabledSslSocketFactory, disabledTrustManager);
				builder.hostnameVerifier(TrustAllHostNames.INSTANCE);
			} catch (NoSuchAlgorithmException | KeyManagementException e) {
				log.warn("Error setting SSLSocketFactory in OKHttpClient", e);
			}
		}
		return builder;
	}

	/**
	 * WebFlux Feign 拦截器
	 *
	 * @return
	 */
//	@Bean
//	public RequestInterceptor feignReactiveRequestInterceptor() {
//		return new FeignReactiveRequestHeaderInterceptor();
//	}
}
