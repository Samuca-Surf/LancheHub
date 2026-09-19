package com.samuca.lanchehub.dto;

import com.samuca.lanchehub.model.UnidadeMedida;

public record IngredienteResumoDTO(
        Long idIngrediente,
        String nome,
        UnidadeMedida unidadeMedida
) {
}
