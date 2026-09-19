package com.samuca.lanchehub.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoResponseDTO(
        Long idProduto,
        String nome,
        String descricao,
        BigDecimal preco,
        String image,
        Boolean disponivel,
        CategoriaResumoDTO categoria,
        List<IngredienteResumoDTO> ingredientes
) {
}
