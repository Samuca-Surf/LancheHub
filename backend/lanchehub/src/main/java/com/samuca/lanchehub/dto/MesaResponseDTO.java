package com.samuca.lanchehub.dto;

import com.samuca.lanchehub.model.StatusMesa;

public record MesaResponseDTO(
        Long idMesa,
        Integer numero,
        StatusMesa statusMesa,
        String qrToken
) {
}
