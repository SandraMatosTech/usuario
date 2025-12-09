package com.sandra.usuario.businness.converter;

import com.sandra.usuario.businness.dto.EnderecoDTO;
import com.sandra.usuario.businness.dto.TelefoneDTO;
import com.sandra.usuario.businness.dto.UsuarioDTO;
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
    public Usuario paraUsuario(UsuarioDTO dto){
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .enderecos(paraListaEnderecoEntity(dto.getEnderecos()))
                .telefones(paraListaTelefoneEntity(dto.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEnderecoEntity(List<EnderecoDTO> dtos){
        return dtos.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO dto){
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .cep(dto.getCep())
                .estado(dto.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefoneEntity(List<TelefoneDTO> dtos){
        return dtos.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO dto){
        return Telefone.builder()
                .ddd(dto.getDdd())
                .numero(dto.getNumero())
                .build();
    }


    // ===============================
    //   ENTITY → DTO
    // ===============================
    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> lista){
        return lista.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco entity){
        return EnderecoDTO.builder()
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .cidade(entity.getCidade())
                .complemento(entity.getComplemento())
                .cep(entity.getCep())
                .estado(entity.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> lista){
        return lista.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone entity){
        return TelefoneDTO.builder()
                .ddd(entity.getDdd())
                .numero(entity.getNumero())
                .build();
    }
}
