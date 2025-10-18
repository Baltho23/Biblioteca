package com.biblioteca.backend.dto;

public record BookStatsDto(
        Long bookId,
        String title,
        Long totalLoans
) {}
