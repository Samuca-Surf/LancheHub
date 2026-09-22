package com.samuca.lanchehub.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull(message = "O id da mesa nao pode estar vazio")
        Long mesaId,
        List<ItemPedidoRequestDTO> itens
) {
}
