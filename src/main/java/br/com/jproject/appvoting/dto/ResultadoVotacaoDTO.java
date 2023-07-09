package br.com.jproject.appvoting.dto;

import br.com.jproject.appvoting.model.Pauta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoVotacaoDTO {
    public Pauta pauta;
    public Integer totalVotos;
    public Integer totalVotosSim;
    public Integer totalVotosNao;
}
