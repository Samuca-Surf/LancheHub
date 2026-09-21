package com.samuca.lanchehub.dto;

import java.util.List;

public record PedidoRequestDTO(
        Long mesaId,
        List<ItemPedidoRequestDTO> itens
) {
}
