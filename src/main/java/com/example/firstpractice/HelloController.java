package com.example.firstpractice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final GreetingProperties greetingProperties;

    public HelloController(GreetingProperties greetingProperties) {
        this.greetingProperties = greetingProperties;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return greetingProperties.getMessage() + " (Author: " + greetingProperties.getAuthor() + ")";
    }
}