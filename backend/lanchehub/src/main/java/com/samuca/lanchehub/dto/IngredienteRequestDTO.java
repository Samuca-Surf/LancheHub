package com.samuca.lanchehub.dto;

import com.samuca.lanchehub.model.UnidadeMedida;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IngredienteRequestDTO(
        @NotBlank(message = "O nome nao pode estar vazio")
        String nome,
        @NotNull(message = "O preco do ingrediente nao pode ser nulo")
        @Min(0)
        BigDecimal preco,
        Boolean disponivel,
        UnidadeMedida unidadeMedida
) {
}
