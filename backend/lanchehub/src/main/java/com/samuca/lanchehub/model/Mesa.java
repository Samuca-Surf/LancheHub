package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;

    private Integer numero;

    @Enumerated(EnumType.STRING)
    private StatusMesa statusMesa;

    private String qrToken;

    @ManyToOne
    @JoinColumn(name = "lanchonete_id")
    private Lanchonete lanchonete;

    public Mesa(){

    }
}
