package com.sandra.usuario.infrastructure.entity.repository;


import com.sandra.usuario.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco,Long> {
}
