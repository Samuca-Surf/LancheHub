package com.samuca.lanchehub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoAtendenteRequestDTO(
        @NotEmpty @Valid List<ItemPedidoRequestDTO> itens
    ) {
}
