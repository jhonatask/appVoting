package br.com.jproject.appvoting;

import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.model.SessaoVotacao;
import br.com.jproject.appvoting.repository.PautaRepository;
import br.com.jproject.appvoting.repository.SessaoVotacaoRepository;
import br.com.jproject.appvoting.services.SessaoVotacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SessaoVotacaoServiceTest {
    private SessaoVotacaoService sessaoVotacaoService;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private SessaoVotacaoRepository sessaoVotacaoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        sessaoVotacaoService = new SessaoVotacaoService(pautaRepository, sessaoVotacaoRepository);
    }

    @Test
    public void testAbrirSessaoVotacao() {
        Long id = 1L;
        int duracaoMinutos = 10;
        LocalDateTime now = LocalDateTime.now();

        Pauta pauta = new Pauta();
        pauta.setId(id);
        pauta.setDescricao("is simply dummy text of the printing and typesetting industry.e");
        pauta.setTitulo("Pauta que esta abrindo a sessao");
        pauta.setDataCriacao(LocalDateTime.now());
        pauta.setDataEncerramento(null);
        pauta.setStatus("E");

        when(pautaRepository.findById(id)).thenReturn(Optional.of(pauta));
        when(sessaoVotacaoRepository.existsByPautaAndDataEncerramentoIsNull(pauta)).thenReturn(false);

        SessaoVotacao sessaoVotacao = new SessaoVotacao();
        sessaoVotacao.setId(1L);
        sessaoVotacao.setPauta(pauta);
        sessaoVotacao.setDataAbertura(now);
        sessaoVotacao.setDataEncerramento(now.plusMinutes(duracaoMinutos));
        sessaoVotacaoService.abrirSessaoVotacao(id, duracaoMinutos);

        verify(pautaRepository, times(1)).findById(id);
        verify(sessaoVotacaoRepository, times(1)).existsByPautaAndDataEncerramentoIsNull(pauta);



    }

    @Test
    public void testAbrirSessaoVotacao_PautaNaoEncontrada() {
        Long pautaId = 1L;
        Integer duracaoMinutos = 10;

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> sessaoVotacaoService.abrirSessaoVotacao(pautaId, duracaoMinutos));

        verify(pautaRepository, times(1)).findById(pautaId);
        verifyNoInteractions(sessaoVotacaoRepository);
    }

    @Test
    public void testAbrirSessaoVotacao_SessaoJaAberta() {
        Long pautaId = 1L;
        Integer duracaoMinutos = 3;

        Pauta pauta = new Pauta();
        pauta.setId(pautaId);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(sessaoVotacaoRepository.existsByPautaAndDataEncerramentoIsNull(pauta)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> sessaoVotacaoService.abrirSessaoVotacao(pautaId, duracaoMinutos));

        verify(pautaRepository, times(1)).findById(pautaId);
        verify(sessaoVotacaoRepository, times(1)).existsByPautaAndDataEncerramentoIsNull(pauta);
        verifyNoMoreInteractions(sessaoVotacaoRepository);
    }
}
