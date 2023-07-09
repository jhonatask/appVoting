package br.com.jproject.appvoting.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoVotacao {

    private Pauta pauta;
    private Integer totalVotos;
    private Integer totalVotosSim;
    private Integer totalVotosNao;

}
