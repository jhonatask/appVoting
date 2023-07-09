package br.com.jproject.appvoting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {

    public Long id;
    public String titulo;
    public String descricao;
    public LocalDateTime dataCriacao;
    public LocalDateTime dataEncerramento;
    public String status;
}
