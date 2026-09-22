package com.samuca.lanchehub.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank(message = "Nome nao pode estar em branco")
        String nome,
        @NotBlank(message = "Descricao não pode estar em branco")
        String descricao
) {
}
