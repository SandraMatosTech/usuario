package com.sandra.usuario.infrastructure.business.converter;
import com.sandra.usuario.infrastructure.entity.Endereco;
import com.sandra.usuario.infrastructure.entity.Telefone;
import com.sandra.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // ===============================
    //   DTO → ENTITY
    // ===============================
    public Usuario paraUsuario(com.sandra.usuario.businness.dto.UsuarioDTO dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(paraListaEnderecoEntity(dto.getEnderecos()))
                .telefones(paraListaTelefoneEntity(dto.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEnderecoEntity(List<com.sandra.usuario.businness.dto.EnderecoDTO> dtos){
        return dtos.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(com.sandra.usuario.businness.dto.EnderecoDTO dto){
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .estado(dto.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefoneEntity(List<com.sandra.usuario.businness.dto.TelefoneDTO> dtos){
        return dtos.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(com.sandra.usuario.businness.dto.TelefoneDTO dto){
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .build();
    }


    // ===============================
    //   ENTITY → DTO
    // ===============================
    public com.sandra.usuario.businness.dto.UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return com.sandra.usuario.businness.dto.UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    public List<com.sandra.usuario.businness.dto.EnderecoDTO> paraListaEnderecoDTO(List<Endereco> lista){
        return lista.stream().map(this::paraEnderecoDTO).toList();
    }

    public com.sandra.usuario.businness.dto.EnderecoDTO paraEnderecoDTO(Endereco entity){
        return com.sandra.usuario.businness.dto.EnderecoDTO.builder()
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .cidade(entity.getCidade())
                .complemento(entity.getComplemento())
                .cep(entity.getCep())
                .estado(entity.getEstado())
                .build();
    }

    public List<com.sandra.usuario.businness.dto.TelefoneDTO> paraListaTelefoneDTO(List<Telefone> lista){
        return lista.stream().map(this::paraTelefoneDTO).toList();
    }

    public com.sandra.usuario.businness.dto.TelefoneDTO paraTelefoneDTO(Telefone entity){
        return com.sandra.usuario.businness.dto.TelefoneDTO.builder()
                .ddd(entity.getDdd())
                .numero(entity.getNumero())
                .build();
    }
}
