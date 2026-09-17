package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIngrediente;

    private String nome;
    private BigDecimal preco;
    private Boolean disponivel;

    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;

    public Ingrediente(){

    }
}
