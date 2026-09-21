package com.samuca.lanchehub.repository;

import com.samuca.lanchehub.model.Mesa;
import com.samuca.lanchehub.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByMesa(Mesa mesa);
}
