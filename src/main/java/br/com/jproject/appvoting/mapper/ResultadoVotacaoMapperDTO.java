package br.com.jproject.appvoting.mapper;


import br.com.jproject.appvoting.dto.ResultadoVotacaoDTO;
import br.com.jproject.appvoting.model.ResultadoVotacao;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ResultadoVotacaoMapperDTO {

    ResultadoVotacaoDTO resultadoVotacaoToResultadoVotacaoDTO(ResultadoVotacao entity);
    ResultadoVotacao resultadoVotacaoDtoToResultadoVotacao(ResultadoVotacaoDTO entity);
}
