package com.sandra.usuario.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "telefone")
@Builder
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Coluna para número completo, incluindo possíveis formatações
    @Column(name = "numero", length = 20, nullable = false)
    private String numero;

    @Column(name = "ddd", length = 3, nullable = false)
    private String ddd;

    // 🔥 CORREÇÃO FUNDAMENTAL (igual ao Endereco)
    // Impede o loop infinito Usuario → Telefones → Usuario → ...
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;
}
