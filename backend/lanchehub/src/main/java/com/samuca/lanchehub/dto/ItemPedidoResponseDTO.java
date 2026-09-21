package com.samuca.lanchehub.dto;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long idItemPedido,
        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}
