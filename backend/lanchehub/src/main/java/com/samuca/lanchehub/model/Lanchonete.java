package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lanchonete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLanchonete;

    private String nome;


    public Lanchonete(){}
    //pertence ao administrador do sistema, ele irá gerenciar as lanchonetes que tiver, algo neste estilo

}
