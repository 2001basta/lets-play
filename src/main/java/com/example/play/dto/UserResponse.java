package com.example.play.dto;

public record UserResponse(
    String id,
    String name,
    String email,
    String role
) {}
