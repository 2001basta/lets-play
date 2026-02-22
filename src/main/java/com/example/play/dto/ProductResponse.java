package com.example.play.dto;

public record ProductResponse(
    String id,
    String name,
    String description,
    Double price,
    String userId
) {}
