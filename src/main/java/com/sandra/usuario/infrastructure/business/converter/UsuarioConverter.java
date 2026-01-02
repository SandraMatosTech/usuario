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

    // ===============================
    //   DTO → ENTITY
    // ===============================
    public Usuario paraUsuario(UsuarioDTO dto) {

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .build();

        // -------- ENDEREÇOS --------
        if (dto.getEnderecos() != null && !dto.getEnderecos().isEmpty()) {
            List<Endereco> enderecos = dto.getEnderecos().stream()
                    .map(endDTO -> {
                        Endereco endereco = paraEndereco(endDTO);
                        endereco.setUsuario(usuario); // 🔥 vínculo obrigatório
                        return endereco;
                    })
                    .toList();

            usuario.setEnderecos(enderecos);
        }

        // -------- TELEFONES --------
        if (dto.getTelefones() != null && !dto.getTelefones().isEmpty()) {
            List<Telefone> telefones = dto.getTelefones().stream()
                    .map(telDTO -> {
                        Telefone telefone = paraTelefone(telDTO);
                        telefone.setUsuario(usuario); // 🔥 vínculo obrigatório
                        return telefone;
                    })
                    .toList();

            usuario.setTelefones(telefones);
        }

        return usuario;
    }

    private Endereco paraEndereco(EnderecoDTO dto) {
        return Endereco.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .build();
    }

    private Telefone paraTelefone(TelefoneDTO dto) {
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .build();
    }

    // ===============================
    //   ENTITY → DTO
    // ===============================
    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {

        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .enderecos(
                        usuario.getEnderecos() != null
                                ? usuario.getEnderecos().stream()
                                .map(this::paraEnderecoDTO)
                                .toList()
                                : List.of()
                )
                .telefones(
                        usuario.getTelefones() != null
                                ? usuario.getTelefones().stream()
                                .map(this::paraTelefoneDTO)
                                .toList()
                                : List.of()
                )
                .build();
    }

    private EnderecoDTO paraEnderecoDTO(Endereco entity) {
        return EnderecoDTO.builder()
                .rua(entity.getRua())
                .numero(entity.getNumero())
                .complemento(entity.getComplemento())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .cep(entity.getCep())
                .build();
    }

    private TelefoneDTO paraTelefoneDTO(Telefone entity) {
        return TelefoneDTO.builder()
                .numero(entity.getNumero())
                .ddd(entity.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .nome(usuarioDTO.getNome() !=null ? usuarioDTO.getNome(): entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ?usuarioDTO.getSenha():entity.getSenha())
                .email(usuarioDTO.getEmail() !=null ? usuarioDTO.getEmail(): entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())

                .build();


    }
}
