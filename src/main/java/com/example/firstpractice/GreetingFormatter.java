package com.example.firstpractice;

/**
 * Decides how the greeting text is assembled from the configured message and author.
 * Which implementation is active is decided by Spring (see FormalGreetingFormatter).
 */
public interface GreetingFormatter {

    String format(String message, String author);
}
