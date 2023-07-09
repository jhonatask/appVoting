package br.com.jproject.appvoting.mapper;

import br.com.jproject.appvoting.dto.PautaDTO;
import br.com.jproject.appvoting.model.Pauta;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PautaMapperDTO {
    PautaDTO pautaToPautaDTO(Pauta entity);
    Pauta pautaDtoToPauta(PautaDTO entity);
}
