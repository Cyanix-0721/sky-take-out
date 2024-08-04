package com.sky.config;

import com.sky.websocket.MyWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置类，用于注册WebSocket处理器
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	/**
	 * 注册WebSocket处理器
	 *
	 * @param registry 用于注册处理器的WebSocketHandlerRegistry
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 注册MyWebSocketHandler，指定端点为"/ws/{sid}"，允许所有来源
		registry.addHandler(new MyWebSocketHandler(), "/ws/{sid}")
				.setAllowedOrigins("*");
	}
}