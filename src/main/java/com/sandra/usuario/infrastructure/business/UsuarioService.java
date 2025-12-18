package com.sandra.usuario.infrastructure.business;

import com.sandra.usuario.infrastructure.business.converter.UsuarioConverter;
import com.sandra.usuario.infrastructure.business.dto.UsuarioDTO;
import com.sandra.usuario.infrastructure.entity.Usuario;
import com.sandra.usuario.infrastructure.exceptions.ConflictException;
import com.sandra.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.sandra.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        boolean existe = verificaEmailExistente(email);

        if (existe) {
            throw new ConflictException("Email já cadastrado: " + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado: " + email));
    }


    public void deletaUsuarioPorEmail(String email){
        if (!usuarioRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("E-mail não encontrado: " + email);
        }
        usuarioRepository.deleteByEmail(email);
    }
}

