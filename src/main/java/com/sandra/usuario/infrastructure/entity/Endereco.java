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

    @Column(name = "usuario_id")
    private Long usuario_id; // Campo para o Converter usar

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false) // Solução da aula
    @JsonIgnore
    private Usuario usuario;
}