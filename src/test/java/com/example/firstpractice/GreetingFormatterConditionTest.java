package com.example.firstpractice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class GreetingFormatterConditionTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner()
            .withUserConfiguration(SimpleGreetingFormatter.class, FormalGreetingFormatter.class);

    @Test
    void formalFormatterIsAbsentByDefault() {
        runner.run(context -> {
            assertThat(context).doesNotHaveBean(FormalGreetingFormatter.class);
            assertThat(context.getBean(GreetingFormatter.class)).isInstanceOf(SimpleGreetingFormatter.class);
        });
    }

    @Test
    void formalFormatterIsAbsentWhenPropertyIsFalse() {
        runner.withPropertyValues("app.greeting.formal=false").run(context ->
                assertThat(context).doesNotHaveBean(FormalGreetingFormatter.class));
    }

    @Test
    void formalFormatterWinsWhenPropertyIsTrue() {
        runner.withPropertyValues("app.greeting.formal=true").run(context -> {
            assertThat(context).hasSingleBean(FormalGreetingFormatter.class);
            assertThat(context.getBean(GreetingFormatter.class)).isInstanceOf(FormalGreetingFormatter.class);
        });
    }
}
