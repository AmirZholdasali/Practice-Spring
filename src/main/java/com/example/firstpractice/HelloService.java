package com.example.firstpractice;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String generateGreeting(String name) {
        return "Hello, " + name + "! Это мой первый REST endpoint.";
    }
}