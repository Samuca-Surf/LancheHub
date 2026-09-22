package com.samuca.lanchehub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoRequestDTO(
        @NotBlank(message = "O nome nao pode estar vazio")
        String nome,
        @NotBlank(message = "A descricao nao pode estar vazio")
        String descricao,
        @NotNull(message = "O preco nao pode ser nulo")
        BigDecimal preco,
        String image,
        Boolean disponivel,
        @NotNull(message = "O id da categoria nao pode ser nula")
        Long categoriaId,
        List<Long> ingredientesIds
) {
}
