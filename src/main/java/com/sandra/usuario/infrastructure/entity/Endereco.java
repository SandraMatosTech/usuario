package com.sandra.usuario.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco")
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;

    private String numero;

    private String complemento;

    private String cidade;

    private String estado;

    private String cep;

    // 🔥 CORREÇÃO ESSENCIAL:
    // Impede loop infinito Usuario → Endereco → Usuario → ...
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;
}
