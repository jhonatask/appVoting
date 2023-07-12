package br.com.jproject.appvoting;

import br.com.jproject.appvoting.dto.VotoDTO;
import br.com.jproject.appvoting.mapper.VotoMapperDTO;
import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.model.ResultadoVotacao;
import br.com.jproject.appvoting.model.Usuario;
import br.com.jproject.appvoting.model.Voto;
import br.com.jproject.appvoting.repository.PautaRepository;
import br.com.jproject.appvoting.repository.UsuarioRepository;
import br.com.jproject.appvoting.repository.VotoRepository;
import br.com.jproject.appvoting.services.VotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VotoServiceTest {
    private VotoService votoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotoMapperDTO votoMapperDTO;

    @Mock
    private VotoRepository votoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        votoService = new VotoService(usuarioRepository, pautaRepository, votoMapperDTO, votoRepository);
    }

    @Test
    public void testVotar() {
        // Given
        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setIdPauta(1L);
        votoDTO.setCpf("12345678900");
        votoDTO.setVoto("SIM");

        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        Voto voto = new Voto();
        voto.setUsuario(usuario);
        voto.setPauta(pauta);
        voto.setVoto("SIM");

        when(usuarioRepository.findByCpf(votoDTO.getCpf())).thenReturn(usuario);
        when(pautaRepository.findById(votoDTO.getIdPauta())).thenReturn(Optional.of(pauta));
        when(votoMapperDTO.votoDtoToVoto(votoDTO)).thenReturn(voto);
        when(votoRepository.existsByUsuarioIdAndPautaId(usuario.getUsuario_id(), votoDTO.getIdPauta())).thenReturn(false);
        when(votoRepository.save(any(Voto.class))).thenReturn(voto);
        when(votoMapperDTO.votoToVotoDTO(voto)).thenReturn(votoDTO);


        VotoDTO resultado = votoService.votar(votoDTO);

        assertNotNull(resultado);
        assertEquals(votoDTO.getIdPauta(), resultado.getIdPauta());
        assertEquals(votoDTO.getCpf(), resultado.getCpf());
        assertEquals(votoDTO.getVoto(), resultado.getVoto());

        verify(usuarioRepository, times(1)).findByCpf(votoDTO.getCpf());
        verify(pautaRepository, times(1)).findById(votoDTO.getIdPauta());
        verify(votoMapperDTO, times(1)).votoDtoToVoto(votoDTO);
        verify(votoRepository, times(1)).existsByUsuarioIdAndPautaId(usuario.getUsuario_id(), votoDTO.getIdPauta());
        verify(votoRepository, times(1)).save(any(Voto.class));
        verify(votoMapperDTO, times(1)).votoToVotoDTO(voto);
    }

    @Test
    public void testVotar_UsuarioNaoEncontrado() {
        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setIdPauta(1L);
        votoDTO.setCpf("12345678900");
        votoDTO.setVoto("SIM");

        when(usuarioRepository.findByCpf(votoDTO.getCpf())).thenReturn(null);
        when(pautaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> votoService.votar(votoDTO));

        verify(usuarioRepository, times(1)).findByCpf(votoDTO.getCpf());
        verify(pautaRepository, times(1)).findById(anyLong());
        verify(votoMapperDTO, never()).votoDtoToVoto(any(VotoDTO.class));
        verify(votoRepository, never()).existsByUsuarioIdAndPautaId(anyLong(), anyLong());
        verify(votoRepository, never()).save(any(Voto.class));
        verify(votoMapperDTO, never()).votoToVotoDTO(any(Voto.class));
    }

    @Test
    public void testVotar_UsuarioJaVotouNaPauta() {
        // Given
        VotoDTO votoDTO = new VotoDTO();
        votoDTO.setIdPauta(1L);
        votoDTO.setCpf("12345678900");
        votoDTO.setVoto("SIM");

        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");

        Pauta pauta = new Pauta();
        pauta.setId(1L);

        when(usuarioRepository.findByCpf(votoDTO.getCpf())).thenReturn(usuario);
        when(pautaRepository.findById(votoDTO.getIdPauta())).thenReturn(Optional.of(pauta));
        when(votoRepository.existsByUsuarioIdAndPautaId(usuario.getUsuario_id(), votoDTO.getIdPauta())).thenReturn(true);


        assertThrows(RuntimeException.class, () -> votoService.votar(votoDTO));

        verify(usuarioRepository, times(1)).findByCpf(votoDTO.getCpf());
        verify(pautaRepository, times(1)).findById(votoDTO.getIdPauta());
        verify(votoMapperDTO, never()).votoDtoToVoto(any(VotoDTO.class));
        verify(votoRepository, times(1)).existsByUsuarioIdAndPautaId(usuario.getUsuario_id(), votoDTO.getIdPauta());
        verify(votoRepository, never()).save(any(Voto.class));
        verify(votoMapperDTO, never()).votoToVotoDTO(any(Voto.class));
    }

    @Test
    public void testObterResultadoVotacao() {

        Long idPauta = 1L;

        Pauta pauta = new Pauta();
        pauta.setId(idPauta);

        Voto voto1 = new Voto();
        voto1.setVoto("SIM");
        Voto voto2 = new Voto();
        voto2.setVoto("SIM");
        Voto voto3 = new Voto();
        voto3.setVoto("NÃO");

        when(pautaRepository.findById(idPauta)).thenReturn(Optional.of(pauta));
        when(votoRepository.findByPauta(pauta)).thenReturn(List.of(voto1, voto2, voto3));


        ResultadoVotacao resultado = votoService.obterResultadoVotacao(idPauta);

        assertNotNull(resultado);
        assertEquals(pauta, resultado.getPauta());
        assertEquals(3, resultado.getTotalVotos());
        assertEquals(2, resultado.getTotalVotosSim());
        assertEquals(1, resultado.getTotalVotosNao());

        verify(pautaRepository, times(1)).findById(idPauta);
        verify(votoRepository, times(1)).findByPauta(pauta);
    }

    @Test
    public void testObterResultadoVotacao_PautaNaoEncontrada() {

        Long id = 1L;
        when(pautaRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        assertThrows(RuntimeException.class, () -> votoService.obterResultadoVotacao(id));

        verify(pautaRepository, times(1)).findById(id);
        verify(votoRepository, never()).findByPauta(any(Pauta.class));
    }
}
