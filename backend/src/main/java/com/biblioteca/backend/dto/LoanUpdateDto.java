package com.biblioteca.backend.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record LoanUpdateDto(
        @NotNull(message = "El ID del libro es obligatorio")
        Long bookId,

        @NotNull(message = "El ID del miembro es obligatorio")
        Long memberId,

        @NotNull(message = "La fecha de préstamo es obligatoria")
        @PastOrPresent(message = "La fecha de préstamo no puede ser futura")
        LocalDate loanDate,

        @NotNull(message = "La fecha de vencimiento es obligatoria")
        LocalDate dueDate,

        @PastOrPresent(message = "La fecha de devolución no puede ser futura")
        LocalDate returnDate
) {
}
