package com.example.firstpractice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * The conditional bean: it exists only when {@code app.greeting.formal=true}.
 * {@code @Primary} makes it win over SimpleGreetingFormatter when both are present.
 */
@Component
@Primary
@ConditionalOnProperty(prefix = "app.greeting", name = "formal", havingValue = "true")
public class FormalGreetingFormatter implements GreetingFormatter {

    @Override
    public String format(String message, String author) {
        return "Dear user, " + message + " Sincerely, " + author + ".";
    }
}
