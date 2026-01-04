package com.sandra.usuario.infrastructure.business;

import com.sandra.usuario.infrastructure.business.converter.UsuarioConverter;
import com.sandra.usuario.infrastructure.business.dto.EnderecoDTO;
import com.sandra.usuario.infrastructure.business.dto.TelefoneDTO;
import com.sandra.usuario.infrastructure.business.dto.UsuarioDTO;
import com.sandra.usuario.infrastructure.entity.Endereco;
import com.sandra.usuario.infrastructure.entity.Telefone;
import com.sandra.usuario.infrastructure.entity.Usuario;
import com.sandra.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.sandra.usuario.infrastructure.security.JwtUtil;
import com.sandra.usuario.repository.EnderecoRepository;
import com.sandra.usuario.repository.TelefoneRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    // Salva um novo usuário e retorna o DTO com o ID gerado pelo banco
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        Usuario salvo = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(salvo);
    }

    // Busca usuário e garante que o ID seja mapeado para o DTO
    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("E-mail não encontrado: " + email));
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    // Atualiza dados básicos e mantém a identidade (ID) do usuário
    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario entity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não localizado"));

        if (dto.getSenha() != null) {
            dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Usuario atualizado = usuarioConverter.updateUsuario(dto, entity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(atualizado));
    }

    // Atualiza endereço específico usando o ID da URL
    public EnderecoDTO atualizaEndereco(Long id, EnderecoDTO dto) {
        Endereco entity = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));

        Endereco atualizado = usuarioConverter.updateEndereco(dto, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(atualizado));
    }

    // Atualiza telefone específico usando o ID da URL
    public TelefoneDTO atualizaTelefone(Long id, TelefoneDTO dto) {
        Telefone entity = telefoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));

        Telefone atualizado = usuarioConverter.updateTelefone(dto, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(atualizado));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}