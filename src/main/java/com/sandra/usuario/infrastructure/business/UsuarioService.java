package com.sandra.usuario.infrastructure.business;


import com.sandra.usuario.businness.dto.UsuarioDTO;
import com.sandra.usuario.infrastructure.business.converter.UsuarioConverter;
import com.sandra.usuario.infrastructure.entity.Usuario;
import com.sandra.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }
}
