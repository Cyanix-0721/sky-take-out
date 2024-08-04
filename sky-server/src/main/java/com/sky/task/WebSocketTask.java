package com.sky.task;

import com.sky.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * WebSocketTask 是一个 Spring 组件，用于定期向 WebSocket 客户端发送消息。
 */
@Component
public class WebSocketTask {
	@Autowired
	private WebSocketServer webSocketServer;

	/**
	 * 每隔5秒向所有连接的 WebSocket 客户端发送消息。
	 * <p>
	 * 消息内容包括当前服务器时间，格式为 HH:mm:ss。
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void sendMessageToClient() {
		webSocketServer.sendToAllClient("这是来自服务端的消息：" + DateTimeFormatter
				.ofPattern("HH:mm:ss")
				.format(LocalDateTime.now()));
	}
}