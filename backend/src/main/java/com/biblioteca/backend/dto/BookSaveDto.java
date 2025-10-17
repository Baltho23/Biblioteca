package com.biblioteca.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record BookSaveDto(
        @NotBlank(message = "El título no puede estar vacío")
        @Size(max = 150, message = "El título no puede tener más de 150 caracteres")
        String title,

        @NotBlank(message = "El autor no puede estar vacío")
        @Size(max = 100, message = "El nombre del autor no puede tener más de 100 caracteres")
        String author,

        @Size(max = 50, message = "El género no puede tener más de 50 caracteres")
        String genre,

        @PositiveOrZero
        Integer copiesAvailable
) {
}
