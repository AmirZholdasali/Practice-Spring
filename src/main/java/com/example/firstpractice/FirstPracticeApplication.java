package com.example.firstpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FirstPracticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstPracticeApplication.class, args);
    }
}