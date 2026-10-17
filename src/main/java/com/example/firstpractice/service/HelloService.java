package com.example.firstpractice.service;

import com.example.firstpractice.formatter.GreetingFormatter;
import com.example.firstpractice.config.GreetingProperties;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final GreetingProperties greetingProperties;
    private final GreetingFormatter greetingFormatter;

    public HelloService(GreetingProperties greetingProperties, GreetingFormatter greetingFormatter) {
        this.greetingProperties = greetingProperties;
        this.greetingFormatter = greetingFormatter;
    }

    public String greet() {
        return greetingFormatter.format(greetingProperties.getMessage(), greetingProperties.getAuthor());
    }
}
