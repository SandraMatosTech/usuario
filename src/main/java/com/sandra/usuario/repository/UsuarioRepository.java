package com.sandra.usuario.repository;

import com.sandra.usuario.infrastructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importação necessária

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    // Alterado para Optional para suportar o .orElseThrow() no Service
    Optional<Usuario> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);
}