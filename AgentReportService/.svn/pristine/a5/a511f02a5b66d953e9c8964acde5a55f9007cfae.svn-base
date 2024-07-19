package com.allconnect.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.allconnect")
@EntityScan(basePackages = "com.allconnect.agentReport.model")
@EnableJpaRepositories(basePackages = "com.allconnect.agentReport.repo")
@EnableTransactionManagement
public class AgentReportService {

	public static void main(String[] args) {
		SpringApplication.run(AgentReportService.class, args);
		
		
	}

}
