package com.biblioteca.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 150, message = "El título no puede tener más de 150 caracteres")
    private String title;

    @NotBlank(message = "El autor no puede estar vacío")
    @Size(max = 100, message = "El nombre del autor no puede tener más de 100 caracteres")
    private String author;

    @Size(max = 50, message = "El género no puede tener más de 50 caracteres")
    private String genre;

    @PositiveOrZero
    private Integer copiesAvailable = 1;
}
