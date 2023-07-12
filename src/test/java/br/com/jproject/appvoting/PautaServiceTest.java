package br.com.jproject.appvoting;

import br.com.jproject.appvoting.dto.PautaDTO;
import br.com.jproject.appvoting.mapper.PautaMapperDTO;
import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.repository.PautaRepository;
import br.com.jproject.appvoting.services.PautaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PautaServiceTest {

    private PautaService pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private PautaMapperDTO pautaMapperDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        pautaService = new PautaService(pautaRepository, pautaMapperDTO, null, null, null, null);
    }

    @Test
    public void testCadastrarPauta() {
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setTitulo("Pauta de Teste");
        pautaDTO.setDescricao("Descrição da Pauta de Teste");

        Pauta pauta = new Pauta();
        pauta.setTitulo("Pauta de Teste");
        pauta.setDescricao("Descrição da Pauta de Teste");

        when(pautaMapperDTO.pautaDtoToPauta(pautaDTO)).thenReturn(pauta);
        when(pautaMapperDTO.pautaToPautaDTO(pauta)).thenReturn(pautaDTO);
        when(pautaRepository.save(pauta)).thenReturn(pauta);

        PautaDTO resultado = pautaService.cadastrarPauta(pautaDTO);

        assertEquals("Pauta de Teste", resultado.getTitulo());
        assertEquals("Descrição da Pauta de Teste", resultado.getDescricao());

        verify(pautaMapperDTO, times(1)).pautaDtoToPauta(pautaDTO);
        verify(pautaMapperDTO, times(1)).pautaToPautaDTO(pauta);
        verify(pautaRepository, times(1)).save(pauta);
    }

    @Test
    public void testListarPautas() {
        List<Pauta> pautas = new ArrayList<>();
        pautas.add(new Pauta(1L, "Pauta 1", "Descricao da pauta 1", LocalDateTime.now(), LocalDateTime.now(), "A"));
        pautas.add(new Pauta(2L, "Pauta 2", "Descrição da Pauta 2", LocalDateTime.now(), LocalDateTime.now(), "A" ));

        when(pautaRepository.findAll()).thenReturn(pautas);

        List<Pauta> resultado = pautaService.listarPautas();

        assertEquals(2, resultado.size());
        assertEquals("Pauta 1", resultado.get(0).getTitulo());
        assertEquals("Descrição da Pauta 2", resultado.get(1).getDescricao());

        verify(pautaRepository, times(1)).findAll();
    }
}

