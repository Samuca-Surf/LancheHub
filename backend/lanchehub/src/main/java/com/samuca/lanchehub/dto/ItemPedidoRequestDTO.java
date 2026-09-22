package com.samuca.lanchehub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
        @NotNull(message = "O id do produto nao pode ser nulo")
        Long produtoId,
        @NotNull(message = "A quantidade nao deve ser nula")
        @Min(0)
        Integer quantidade
) {
}
