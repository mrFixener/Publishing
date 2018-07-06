package com.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Dimon on 04.07.2018.
 */
@EntityScan(basePackages = "com.task")
@EnableJpaRepositories("com.task.repository")
@SpringBootApplication(scanBasePackages = {"com.task"})
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
