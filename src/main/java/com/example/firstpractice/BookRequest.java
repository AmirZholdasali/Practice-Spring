package com.example.firstpractice;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookRequest(
        @NotBlank(message = "must not be blank") String title,
        @Min(value = 1, message = "must be at least 1") int pages) {}

