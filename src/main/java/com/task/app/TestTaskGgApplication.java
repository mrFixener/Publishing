package com.task.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.task")
@EnableJpaRepositories("com.task.repository")
@SpringBootApplication(scanBasePackages = {"com.task"})
public class TestTaskGgApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestTaskGgApplication.class, args);
    }
}
