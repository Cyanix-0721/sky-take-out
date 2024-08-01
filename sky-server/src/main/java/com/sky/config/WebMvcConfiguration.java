package com.sky.config;

import com.sky.interceptor.JwtTokenAdminInterceptor;
import com.sky.interceptor.JwtTokenUserInterceptor;
import com.sky.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

	@Autowired
	private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
	@Autowired
	private JwtTokenUserInterceptor jwtTokenUserInterceptor;

	/**
	 * 注册自定义拦截器
	 *
	 * @param registry 拦截器注册表
	 */
	protected void addInterceptors(InterceptorRegistry registry) {
		log.info("开始注册自定义拦截器...");
		registry.addInterceptor(jwtTokenAdminInterceptor)
				.addPathPatterns("/admin/**")
				.excludePathPatterns("/admin/employee/login");
		registry.addInterceptor(jwtTokenUserInterceptor)
				.addPathPatterns("/user/**")
				.excludePathPatterns("/user/user/login")
				.excludePathPatterns("/user/shop/status");
	}

	/**
	 * 设置静态资源映射
	 *
	 * @param registry 资源处理器注册表
	 */
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * 扩展Spring MVC框架的消息转化器
	 * <p>
	 * 该方法用于扩展Spring MVC框架的消息转换器列表，添加自定义的消息转换器。
	 *
	 * @param converters 消息转换器列表
	 */
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		log.info("扩展消息转换器...");
		// 创建一个消息转换器对象
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		// 需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
		converter.setObjectMapper(new JacksonObjectMapper());
		// 将自己的消息转化器加入容器中
		converters.add(0, converter);
	}
}