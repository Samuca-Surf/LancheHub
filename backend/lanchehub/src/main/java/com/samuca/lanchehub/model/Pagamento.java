package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Pedido pedido;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private LocalDateTime dataHora;

    public Pagamento(){}
}
