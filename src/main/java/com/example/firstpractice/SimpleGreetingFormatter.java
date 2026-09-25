package com.example.firstpractice;

import org.springframework.stereotype.Component;

/** Default formatter: always registered, used when the formal one is switched off. */
@Component
public class SimpleGreetingFormatter implements GreetingFormatter {

    @Override
    public String format(String message, String author) {
        return message + " (Author: " + author + ")";
    }
}
