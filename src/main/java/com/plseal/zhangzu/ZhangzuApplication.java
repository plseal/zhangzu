package com.plseal.zhangzu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class ZhangzuApplication {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ZhangzuApplication.class);
		application.addListeners(new ApplicationPidFileWriter("./app.pid"));
		application.run(args);
	}

}
