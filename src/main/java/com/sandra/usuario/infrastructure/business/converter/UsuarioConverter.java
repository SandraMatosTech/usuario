package com.sandra.usuario.infrastructure.business.converter;

import com.sandra.usuario.infrastructure.business.dto.EnderecoDTO;
import com.sandra.usuario.infrastructure.business.dto.TelefoneDTO;
import com.sandra.usuario.infrastructure.business.dto.UsuarioDTO;
import com.sandra.usuario.infrastructure.entity.Endereco;
import com.sandra.usuario.infrastructure.entity.Telefone;
import com.sandra.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // ESTE MÉTODO ESTAVA FALTANDO E CAUSA O ERRO NO SERVICE
    public Usuario paraUsuario(UsuarioDTO dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .enderecos(usuario.getEnderecos() != null ?
                        usuario.getEnderecos().stream().map(this::paraEnderecoDTO).toList() : List.of())
                .telefones(usuario.getTelefones() != null ?
                        usuario.getTelefones().stream().map(this::paraTelefoneDTO).toList() : List.of())
                .build();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco entity) {
        return EnderecoDTO.builder()
                .id(entity.getId())
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .cep(entity.getCep())
                .build();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone entity) {
        return TelefoneDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .ddd(entity.getDdd())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO dto, Long idUsuario) {
        return Endereco.builder()
                .rua(dto.getRua())
                .estado(dto.getEstado())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .cep(dto.getCep())
                .usuario_id(idUsuario) // Resolvido com a solução da aula
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO dto, Long idUsuario) {
        return Telefone.builder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuario_id(idUsuario) // Resolvido com a solução da aula
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO dto, Usuario entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .email(dto.getEmail() != null ? dto.getEmail() : entity.getEmail())
                .senha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha())
                .build();
    }
}