package com.biblioteca.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="book_id")
    @NotNull(message = "El libro es obligatorio")
    private Book book;

    @ManyToOne
    @JoinColumn(name="member_id")
    @NotNull(message = "El miembro es obligatorio")
    private Member member;

    @NotNull(message = "La fecha de préstamo es obligatoria")
    @PastOrPresent(message = "La fecha de préstamo no puede ser futura")
    private LocalDate loanDate;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @FutureOrPresent(message = "La fecha de vencimiento no puede ser anterior al préstamo")
    private LocalDate dueDate;

    @PastOrPresent(message = "La fecha de devolución no puede ser futura")
    private LocalDate returnDate;
}
