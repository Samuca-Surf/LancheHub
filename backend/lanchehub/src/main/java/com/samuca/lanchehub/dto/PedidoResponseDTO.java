package com.samuca.lanchehub.dto;

import com.samuca.lanchehub.model.StatusPagamento;
import com.samuca.lanchehub.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long idPedido,
        LocalDateTime dataHora,
        StatusPedido statusPedido,
        StatusPagamento statusPagamento,
        Long mesaId,
        BigDecimal valorTotal,
        List<ItemPedidoResponseDTO> itens
) {
}
