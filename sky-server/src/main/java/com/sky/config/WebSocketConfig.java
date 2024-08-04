package com.sky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置类，用于注册WebSocket的Bean
 */
@Configuration
public class WebSocketConfig {

	/**
	 * 创建并返回一个ServerEndpointExporter Bean
	 * <p>
	 * ServerEndpointExporter Bean用于自动注册使用@ServerEndpoint注解声明的WebSocket端点
	 *
	 * @return ServerEndpointExporter Bean
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}