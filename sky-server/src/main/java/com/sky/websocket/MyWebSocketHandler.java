package com.sky.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket服务
 */
@Slf4j
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

	//存放会话对象
	private static final Map<String, WebSocketSession> sessionMap = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		try {
			String sid = session.getId();
			System.out.println("客户端: " + sid + " 建立连接");
			sessionMap.put(sid, session);
		} catch (Exception e) {
			log.error("Error establishing connection: {}", session.getId(), e);
			throw e;
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		try {
			String sid = session.getId();
			System.out.println("收到来自客户端: " + sid + " 的信息: " + message.getPayload());
		} catch (Exception e) {
			log.error("Error handling message: {}", session.getId(), e);
			throw e;
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		try {
			String sid = session.getId();
			System.out.println("连接断开: " + sid);
			sessionMap.remove(sid);
		} catch (Exception e) {
			log.error("Error closing connection: {}", session.getId(), e);
			throw e;
		}
	}

	/**
	 * 群发消息
	 *
	 * @param message 要发送的消息
	 */
	public void sendToAllClient(String message) {
		sessionMap.values().forEach(session -> {
			try {
				// 服务器向客户端发送消息
				session.sendMessage(new TextMessage(message));
			} catch (Exception e) {
				// 使用日志记录异常
				log.error("Error sending message to client: {}", session.getId(), e);
			}
		});
	}
}