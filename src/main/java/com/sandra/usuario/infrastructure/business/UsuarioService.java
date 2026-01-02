package com.sandra.usuario.infrastructure.business;

import com.sandra.usuario.infrastructure.business.converter.UsuarioConverter;
import com.sandra.usuario.infrastructure.business.dto.UsuarioDTO;
import com.sandra.usuario.infrastructure.entity.Usuario;
import com.sandra.usuario.infrastructure.exceptions.ConflictException;
import com.sandra.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.sandra.usuario.infrastructure.security.JwtUtil;
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
    private final JwtUtil jwtUtil;

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

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado: " + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        if (!usuarioRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("E-mail não encontrado: " + email);
        }
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        // Extrai o email do token (removendo o prefixo "Bearer ")
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setSenha(dto.getSenha() != null? passwordEncoder.encode(dto.getSenha()):null);
        // Busca o usuário existente no banco
        Usuario usuarioEntity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não localizado"));

        // OPÇÃO RECOMENDADA: Usa o converter para atualizar os campos básicos (nome, email, etc)
        usuarioConverter.updateUsuario(dto, usuarioEntity);



        // Salva a entidade atualizada e retorna o DTO resultante
        Usuario usuarioSalvo = usuarioRepository.save(usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioSalvo);
    }
}