package com.example.play.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
    @NotBlank String name,
    String description,
    @Positive Double price
) {}
