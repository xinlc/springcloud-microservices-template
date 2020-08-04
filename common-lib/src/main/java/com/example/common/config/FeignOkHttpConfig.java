package com.example.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.example.common.interceptor.FeignRequestHeaderInterceptor;
import com.example.common.interceptor.OkHttpLogInterceptor;
import com.example.common.logger.OkHttpSlf4jLogger;
import com.example.common.ssl.DisableValidationTrustManager;
import com.example.common.ssl.TrustAllHostNames;
import com.example.common.utils.Holder;
import feign.Client;
import feign.Feign;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
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
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import(value = {OkHttpProps.class})
@Slf4j
public class FeignOkHttpConfig {

	@Autowired
	private OkHttpProps okHttpProps;

	@Bean
	public Encoder feignEncoder() {
		return new SpringEncoder(feignHttpMessageConverter());
	}

	@Bean
	public Decoder feignDecoder() {
		return new SpringDecoder(feignHttpMessageConverter());
	}

	/**
	 * 设置解码器为 fastjson
	 *
	 * @return
	 */
	private ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
		final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(this.getFastJsonConverter());
		return () -> httpMessageConverters;
	}

	private FastJsonHttpMessageConverter getFastJsonConverter() {
		// 创建fastJson消息转换器
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		converter.setSupportedMediaTypes(supportedMediaTypes);

		// 创建配置类
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.getSerializeConfig().put(JSON.class, new SwaggerJsonSerializer());
		fastJsonConfig.setSerializerFeatures(
				SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.WriteMapNullValue
		);

		converter.setFastJsonConfig(fastJsonConfig);
		return converter;
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
	 * 定义 Feign 拦截器，添加请求头
	 *
	 * @return
	 */
	@Bean
	public RequestInterceptor feignRequestInterceptor() {
		return new FeignRequestHeaderInterceptor();
	}

	/**
	 * 自定义错误
	 */
//	@Bean
//	public ErrorDecoder errorDecoder() {
//		return new UserErrorDecoder();
//	}

	/**
	 * 自定义错误
	 */
//	public class UserErrorDecoder implements ErrorDecoder {
//		private Logger logger = LoggerFactory.getLogger(getClass());
//		@Override
//		public Exception decode(String methodKey, Response response) {
//			Exception exception = null;
//			try {
//				String json = Util.toString(response.body().asReader());
//				exception = new RuntimeException(json);
//				Result result = JsonMapper.nonEmptyMapper().fromJson(json, Result.class);
//				// 业务异常包装成 HystrixBadRequestException，不进入熔断逻辑
//				if (!result.isSuccess()) {
//					exception = new HystrixBadRequestException(result.getMessage());
//				}
//			} catch (IOException ex) {
//				logger.error(ex.getMessage(), ex);
//			}
//			return exception;
//		}
//	}
}

