package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Điểm khởi chạy đồng thời nạp các thành phần của phân hệ Student OJT. */
@SpringBootApplication(scanBasePackages = {"com.example.demo", "com.r2s.masterclass.student"})
@EntityScan(basePackages = "com.r2s.masterclass.student.entity")
@EnableJpaRepositories(basePackages = "com.r2s.masterclass.student.repository")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
