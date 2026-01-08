package com.sandra.usuario.infrastructure.business;

import com.sandra.usuario.infrastructure.business.converter.UsuarioConverter;
import com.sandra.usuario.infrastructure.business.dto.EnderecoDTO;
import com.sandra.usuario.infrastructure.business.dto.TelefoneDTO;
import com.sandra.usuario.infrastructure.business.dto.UsuarioDTO;
import com.sandra.usuario.infrastructure.entity.Endereco;
import com.sandra.usuario.infrastructure.entity.Telefone;
import com.sandra.usuario.infrastructure.entity.Usuario;
import com.sandra.usuario.infrastructure.exceptions.ConflictException;
import com.sandra.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.sandra.usuario.infrastructure.security.JwtUtil;
import com.sandra.usuario.repository.EnderecoRepository;
import com.sandra.usuario.repository.TelefoneRepository;
import com.sandra.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new ConflictException("Email já cadastrado");
        }
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = extrairEmailDoToken(token);
        Usuario usuarioExistente = buscarUsuarioPorEmail(email);

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            dto.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Usuario usuarioAtualizado = usuarioConverter.updateUsuario(dto, usuarioExistente);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuarioAtualizado));
    }

    /**
     * Cadastra um novo endereço vinculado ao usuário extraído do token.
     */
    public EnderecoDTO cadastroEndereco(String token, EnderecoDTO dto) {
        // O substring(7) acontece dentro deste método auxiliar
        String email = extrairEmailDoToken(token);
        Usuario usuario = buscarUsuarioPorEmail(email);

        // Converte o DTO para Entidade passando o ID do usuário para o relacionamento
        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        return usuarioConverter.paraEnderecoDTO(enderecoSalvo);
    }

    /**
     * Cadastra um novo telefone vinculado ao usuário extraído do token.
     */
    public TelefoneDTO cadastroTelefone(String token, TelefoneDTO dto) {
        // O substring(7) acontece dentro deste método auxiliar
        String email = extrairEmailDoToken(token);
        Usuario usuario = buscarUsuarioPorEmail(email);

        // Converte o DTO para Entidade passando o ID do usuário para o relacionamento
        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        Telefone telefoneSalvo = telefoneRepository.save(telefone);

        return usuarioConverter.paraTelefoneDTO(telefoneSalvo);
    }

    /**
     * Método auxiliar para limpar o prefixo 'Bearer ' e extrair o e-mail.
     */
    private String extrairEmailDoToken(String token) {
        // Se o token começar com "Bearer ", remove os 7 primeiros caracteres
        String tokenLimpo = (token != null && token.startsWith("Bearer "))
                ? token.substring(7)
                : token;
        return jwtUtil.extrairEmailToken(tokenLimpo);
    }
}