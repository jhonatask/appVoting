package br.com.jproject.appvoting.mapper;

import br.com.jproject.appvoting.dto.VotoDTO;
import br.com.jproject.appvoting.model.Voto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface VotoMapperDTO {
    VotoDTO votoToVotoDTO(Voto entity);
    Voto votoDtoToVoto(VotoDTO entity);
}
