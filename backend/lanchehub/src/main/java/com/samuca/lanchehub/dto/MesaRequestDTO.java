package com.samuca.lanchehub.dto;

import jakarta.validation.constraints.NotNull;

public record MesaRequestDTO(
        @NotNull(message = "O numero da mesa nao pode ser nulo")
        Integer numeroMesa
) {
}
