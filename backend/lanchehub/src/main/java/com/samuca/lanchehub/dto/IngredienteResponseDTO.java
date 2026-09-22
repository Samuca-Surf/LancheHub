package com.samuca.lanchehub.dto;

import com.samuca.lanchehub.model.UnidadeMedida;

import java.math.BigDecimal;

public record IngredienteResponseDTO(
        Long idIngrediente,
        String nome,
        BigDecimal preco,
        Boolean disponivel,
        UnidadeMedida unidadeMedida
) {
}
