package br.com.jproject.appvoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    public Long usuario_id;
    public String cpf;
    public String nome;
}
