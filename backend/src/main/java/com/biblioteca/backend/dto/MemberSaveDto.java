package com.biblioteca.backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberSaveDto(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
        String name,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "El correo electrónico no tiene un formato válido")
        @Size(max = 150, message = "El correo no puede tener más de 150 caracteres")
        @Column(unique = true)
        String email
) {
}
