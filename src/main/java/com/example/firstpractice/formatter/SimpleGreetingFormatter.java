package com.example.firstpractice.formatter;

import org.springframework.stereotype.Component;


@Component
public class SimpleGreetingFormatter implements GreetingFormatter {

    @Override
    public String format(String message, String author) {
        return message + " (Author: " + author + ")";
    }
}
