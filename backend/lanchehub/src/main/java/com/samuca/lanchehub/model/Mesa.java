package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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

    @Column(unique = true, nullable = false, updatable = false, length = 36)
    private String qrToken;

    @ManyToOne
    @JoinColumn(name = "lanchonete_id")
    private Lanchonete lanchonete;

    public Mesa(){

    }

    @PrePersist
    void gerarToken(){
        if (qrToken == null){
            qrToken = UUID.randomUUID().toString();
        }
    }
}
