package com.samuca.lanchehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfilUsuario;

    @ManyToOne
    private Lanchonete lanchonete;

    public Usuario(){

    }

    //o atendente também poderá realizar pedidos na determinada mesa sem precisar escanear QrCode ,
}
