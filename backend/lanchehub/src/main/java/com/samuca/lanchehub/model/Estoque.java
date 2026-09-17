package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ingrediente ingrediente;

    private BigDecimal quantidadeAtual;

    private BigDecimal quantidadeMinima;

    public Estoque(){

    }
}
