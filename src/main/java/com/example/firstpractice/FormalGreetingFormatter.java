package com.example.firstpractice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component
@Primary
@ConditionalOnProperty(prefix = "app.greeting", name = "formal", havingValue = "true")
public class FormalGreetingFormatter implements GreetingFormatter {

    @Override
    public String format(String message, String author) {
        return "Dear user, " + message + " Sincerely, " + author + ".";
    }
}
