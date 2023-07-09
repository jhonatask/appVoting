package br.com.jproject.appvoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoDTO {
    public String cpf;
    public Long idPauta;
    public String voto;
}
