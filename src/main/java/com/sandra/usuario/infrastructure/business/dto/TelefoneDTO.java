package com.sandra.usuario.infrastructure.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {
    private String numero;
    private String ddd;
}
