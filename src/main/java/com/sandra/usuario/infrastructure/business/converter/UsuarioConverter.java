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

    public Usuario paraUsuario(UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();

        if (dto.getEnderecos() != null) {
            usuario.setEnderecos(dto.getEnderecos().stream()
                    .map(endDTO -> {
                        Endereco end = paraEndereco(endDTO);
                        end.setUsuario(usuario);
                        return end;
                    }).toList());
        }

        if (dto.getTelefones() != null) {
            usuario.setTelefones(dto.getTelefones().stream()
                    .map(telDTO -> {
                        Telefone tel = paraTelefone(telDTO);
                        tel.setUsuario(usuario);
                        return tel;
                    }).toList());
        }
        return usuario;
    }

    private Endereco paraEndereco(EnderecoDTO dto) {
        return Endereco.builder()
                .id(dto.getId()) // ADICIONADO: Importante se você for salvar algo que já tem ID
                .rua(dto.getRua()).numero(dto.getNumero())
                .complemento(dto.getComplemento()).cidade(dto.getCidade())
                .estado(dto.getEstado()).cep(dto.getCep())
                .build();
    }

    private Telefone paraTelefone(TelefoneDTO dto) {
        return Telefone.builder()
                .id(dto.getId()) // ADICIONADO
                .numero(dto.getNumero()).ddd(dto.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId()) // CORREÇÃO: Mapeia o ID do usuário
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
                .id(entity.getId()) // CORREÇÃO: Isso fará o número do ID aparecer no JSON do endereço
                .rua(entity.getRua()).numero(entity.getNumero())
                .complemento(entity.getComplemento()).cidade(entity.getCidade())
                .estado(entity.getEstado()).cep(entity.getCep())
                .build();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone entity) {
        return TelefoneDTO.builder()
                .id(entity.getId()) // CORREÇÃO: Isso fará o número do ID aparecer no JSON do telefone
                .numero(entity.getNumero()).ddd(entity.getDdd())
                .build();
    }

    // Os métodos de UPDATE abaixo já estavam corretos, pois mantêm o ID da entidade original.
    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .usuario(entity.getUsuario())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO dto, Usuario entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .email(dto.getEmail() != null ? dto.getEmail() : entity.getEmail())
                .senha(dto.getSenha() != null ? dto.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .usuario(entity.getUsuario())
                .build();
    }
}