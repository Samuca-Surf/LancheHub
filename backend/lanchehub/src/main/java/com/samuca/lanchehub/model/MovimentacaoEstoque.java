package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MovimentacaoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimentacao;

    @ManyToOne
    private Ingrediente ingrediente;

    private BigDecimal quantidade;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private LocalDateTime data;

    private String motivo;

}
