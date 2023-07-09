package br.com.jproject.appvoting.mapper;


import br.com.jproject.appvoting.dto.UsuarioDTO;
import br.com.jproject.appvoting.model.Usuario;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UsuarioMapperDTO {

    UsuarioDTO usuarioToUsuarioDTO(Usuario entity);
    Usuario usuarioDtoToUsuario(UsuarioDTO entity);
}
