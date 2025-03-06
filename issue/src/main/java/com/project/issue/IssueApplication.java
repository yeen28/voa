package com.project.issue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication (
		scanBasePackages = {"com.project.issue", "com.project.modulecommon"}
)
@EnableJpaRepositories(basePackages = "com.project.modulecommon.repository")
@EntityScan("com.project.modulecommon.domain")
public class IssueApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueApplication.class, args);
	}

}
