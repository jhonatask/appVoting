package br.com.jproject.appvoting.services;


import br.com.jproject.appvoting.dto.UsuarioDTO;
import br.com.jproject.appvoting.mapper.UsuarioMapperDTO;
import br.com.jproject.appvoting.model.Usuario;
import br.com.jproject.appvoting.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapperDTO usuarioMapperDTO;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapperDTO usuarioMapperDTO) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapperDTO = usuarioMapperDTO;
    }

    @Transactional
    public UsuarioDTO criarUsuario(UsuarioDTO usuario){
        Usuario newUsuario = usuarioMapperDTO.usuarioDtoToUsuario(usuario);
        newUsuario.setCpf(usuario.cpf);
        newUsuario.setNome(usuario.nome);
        usuarioRepository.save(newUsuario);
        return usuarioMapperDTO.usuarioToUsuarioDTO(newUsuario);
    }
}
