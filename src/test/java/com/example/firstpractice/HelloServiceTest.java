package com.example.firstpractice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** No Spring here: constructor injection lets us build the service by hand. */
class HelloServiceTest {

    @Test
    void greetPassesConfiguredValuesToTheFormatter() {
        GreetingProperties properties = new GreetingProperties();
        properties.setMessage("Hi");
        properties.setAuthor("Bob");

        HelloService service = new HelloService(properties, (message, author) -> message + "|" + author);

        assertEquals("Hi|Bob", service.greet());
    }
}
