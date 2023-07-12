package br.com.jproject.appvoting;

import br.com.jproject.appvoting.dto.UsuarioDTO;
import br.com.jproject.appvoting.mapper.UsuarioMapperDTO;
import br.com.jproject.appvoting.model.Usuario;
import br.com.jproject.appvoting.repository.UsuarioRepository;
import br.com.jproject.appvoting.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsuarioServiceTest {
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapperDTO usuarioMapperDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository, usuarioMapperDTO);
    }

    @Test
    public void testCriarUsuario() {
        // Given
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCpf("123456789");
        usuarioDTO.setNome("John Doe");

        Usuario usuario = new Usuario();
        usuario.setCpf("123456789");
        usuario.setNome("John Doe");

        when(usuarioMapperDTO.usuarioDtoToUsuario(usuarioDTO)).thenReturn(usuario);
        when(usuarioMapperDTO.usuarioToUsuarioDTO(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO result = usuarioService.criarUsuario(usuarioDTO);

        verify(usuarioMapperDTO).usuarioDtoToUsuario(usuarioDTO);
        verify(usuarioRepository).save(usuario);
        verify(usuarioMapperDTO).usuarioToUsuarioDTO(usuario);

    }
}
