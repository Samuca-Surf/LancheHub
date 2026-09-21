package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
    //colocar valor total do itemPedido
    private BigDecimal valorTotal;

    public Pedido(){

    }
}
