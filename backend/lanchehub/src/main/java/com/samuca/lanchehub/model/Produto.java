package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String image;
    private Boolean disponivel;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToMany
    private List<Ingrediente> ingredientes;

    @ManyToOne
    @JoinColumn(name = "lanchonete_id")
    private Lanchonete lanchonete;

    public Produto(){

    }
}
