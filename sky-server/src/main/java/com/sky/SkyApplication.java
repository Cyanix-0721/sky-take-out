package com.sky;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;

@SpringBootApplication
@EnableAspectJAutoProxy // 开启AOP代理
@EnableScheduling // 开启定时任务
@EnableTransactionManagement // 开启注解方式的事务管理
@Slf4j
public class SkyApplication {
	private static final Logger logger = LoggerFactory.getLogger(SkyApplication.class);

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SkyApplication.class);
		ConfigurableApplicationContext application = app.run(args);
		Environment env = application.getEnvironment();
		logger.info("\n----------------------------------------------------------\n\t" +
						"Application '{}' is running! Access URLs:\n\t" +
						"Local: \t\thttp://localhost:{}\n\t" +
						"Local_Doc: \t\thttp://localhost:{}/doc.html\n\t" +
						"External: \thttp://{}:{}\n\t" +
						"External_Doc: \thttp://{}:{}/doc.html\n" +
						"----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				env.getProperty("server.port"),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));
	}

}
