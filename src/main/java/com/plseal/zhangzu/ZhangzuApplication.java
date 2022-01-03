package com.plseal.zhangzu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ZhangzuApplication {
	private static final Logger logger = LoggerFactory.getLogger(ZhangzuApplication.class);
	public static void main(String[] args) {
		logger.info("http://localhost:8080/get_one_zhangzu?flg=Select&z_date=2020/06/23&z_name=%E5%9C%B0%E9%93%81%E8%B6%85%E5%B8%82");
		SpringApplication.run(ZhangzuApplication.class, args);
	}

}
