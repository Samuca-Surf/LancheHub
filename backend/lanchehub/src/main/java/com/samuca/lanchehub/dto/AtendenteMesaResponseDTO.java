package com.samuca.lanchehub.dto;

import com.samuca.lanchehub.model.StatusMesa;

public record AtendenteMesaResponseDTO(
        Long idMesa,
        Integer numero,
        StatusMesa statusMesa
) {
}
