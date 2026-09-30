package com.samuca.lanchehub.repository;

import com.samuca.lanchehub.model.Mesa;
import com.samuca.lanchehub.model.Pedido;
import com.samuca.lanchehub.model.StatusPagamento;
import com.samuca.lanchehub.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByMesa(Mesa mesa);
    List<Pedido> findByStatusPedidoInOrderByDataHoraAsc(List<StatusPedido> status);
    List<Pedido> findByStatusPedidoOrderByDataHoraAsc(StatusPedido statusPedido);
    List<Pedido> findByStatusPagamentoAndStatusPedidoNotOrderByDataHoraAsc(StatusPagamento statusPagamento  , StatusPedido statusPedido);
}
