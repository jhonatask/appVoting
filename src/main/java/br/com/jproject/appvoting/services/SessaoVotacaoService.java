package br.com.jproject.appvoting.services;

import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.model.SessaoVotacao;
import br.com.jproject.appvoting.repository.PautaRepository;
import br.com.jproject.appvoting.repository.SessaoVotacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessaoVotacaoService {

    private final PautaRepository pautaRepository;
    private final SessaoVotacaoRepository sessaoVotacaoRepository;

    public SessaoVotacaoService(PautaRepository pautaRepository, SessaoVotacaoRepository sessaoVotacaoRepository) {
        this.pautaRepository = pautaRepository;
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
    }

    @Transactional
    public void abrirSessaoVotacao(Long pautaId, Integer duracaoMinutos) {
        Optional<Pauta> pauta = pautaRepository.findById(pautaId);
        if (!pauta.isPresent()){
            throw new RuntimeException("Pauta não encontrada");
        }
        boolean sessoesAbertas = sessaoVotacaoRepository.existsByPautaAndDataEncerramentoIsNull(pauta.get());
        if (sessoesAbertas) {
            throw new RuntimeException("Já existe uma sessão de votação aberta para essa pauta");
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dataEncerramento = now.plusMinutes(duracaoMinutos != null ? duracaoMinutos : SessaoVotacao.DEFAULT_DURACAO_MINUTOS);
        SessaoVotacao sessaoVotacao = new SessaoVotacao();
        sessaoVotacao.setPauta(pauta.get());
        sessaoVotacao.setDataAbertura(now);
        sessaoVotacao.setDataEncerramento(dataEncerramento);
        sessaoVotacaoRepository.save(sessaoVotacao);
    }
}
