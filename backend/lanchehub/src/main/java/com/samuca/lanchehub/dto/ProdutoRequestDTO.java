package com.samuca.lanchehub.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoRequestDTO(
        String nome,
        String descricao,
        BigDecimal preco,
        String image,
        Boolean disponivel,
        Long categoriaId,
        List<Long> ingredientesIds
) {
}
