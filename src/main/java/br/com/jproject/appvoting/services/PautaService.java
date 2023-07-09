package br.com.jproject.appvoting.services;


import br.com.jproject.appvoting.dto.PautaDTO;
import br.com.jproject.appvoting.dto.VotoDTO;
import br.com.jproject.appvoting.mapper.PautaMapperDTO;
import br.com.jproject.appvoting.mapper.VotoMapperDTO;
import br.com.jproject.appvoting.model.*;
import br.com.jproject.appvoting.repository.PautaRepository;
import br.com.jproject.appvoting.repository.SessaoVotacaoRepository;
import br.com.jproject.appvoting.repository.UsuarioRepository;
import br.com.jproject.appvoting.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PautaService {
        private final PautaRepository pautaRepository;
        private final PautaMapperDTO pautaMapperDTO;
        private final SessaoVotacaoRepository sessaoVotacaoRepository;
        private final UsuarioRepository usuarioRepository;
        private final VotoRepository votoRepository;
        private final VotoMapperDTO votoMapperDTO;


    public PautaService(PautaRepository pautaRepository, PautaMapperDTO pautaMapperDTO, SessaoVotacaoRepository sessaoVotacaoRepository, UsuarioRepository usuarioRepository, VotoRepository votoRepository, VotoMapperDTO votoMapperDTO) {
        this.pautaRepository = pautaRepository;
        this.pautaMapperDTO = pautaMapperDTO;
        this.sessaoVotacaoRepository = sessaoVotacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.votoRepository = votoRepository;
        this.votoMapperDTO = votoMapperDTO;
    }

    @Transactional
    public PautaDTO cadastrarPauta(PautaDTO pautaDTO){
        Pauta novaPauta = pautaMapperDTO.pautaDtoToPauta(pautaDTO);
        novaPauta.setDataCriacao(LocalDateTime.now());
        novaPauta.setDescricao(pautaDTO.descricao);
        novaPauta.setTitulo(pautaDTO.titulo);
        novaPauta.setStatus(Pauta.ABERTA);
        pautaRepository.save(novaPauta);
        return pautaMapperDTO.pautaToPautaDTO(novaPauta);
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

        LocalDateTime dataEncerramento = LocalDateTime.now().plusMinutes(duracaoMinutos != null ? duracaoMinutos : SessaoVotacao.DEFAULT_DURACAO_MINUTOS);
        SessaoVotacao sessaoVotacao = new SessaoVotacao();
        sessaoVotacao.setPauta(pauta.get());
        sessaoVotacao.setDataAbertura(LocalDateTime.now());
        sessaoVotacao.setDataEncerramento(dataEncerramento);
        sessaoVotacaoRepository.save(sessaoVotacao);
    }

    @Transactional
    public VotoDTO votar(VotoDTO voto) {
        Usuario usuario = usuarioRepository.findByCpf(voto.cpf);
        Optional<Pauta> pauta = pautaRepository.findById(voto.idPauta);
        if (usuario == null){
            throw new RuntimeException("Usuario não encontrado para este cpf informado");
        }
        boolean jaVotouSessao = votoRepository.existsByUsuarioIdAndPautaId(usuario.getUsuario_id(), voto.idPauta);
        if (jaVotouSessao){
            throw new RuntimeException("Usuario informado ja votou para esta pauta");
        }
        Voto newVoto = votoMapperDTO.votoDtoToVoto(voto);
        newVoto.setUsuario(usuario);
        newVoto.setPauta(pauta.get());
        newVoto.setVoto(voto.voto);
        votoRepository.save(newVoto);
        return votoMapperDTO.votoToVotoDTO(newVoto);
    }


    public ResultadoVotacao obterResultadoVotacao(Long idPauta) {

        Optional<Pauta> pauta = pautaRepository.findById(idPauta);

        List<Voto> votos = votoRepository.findByPauta(pauta.get());
        int totalVotos = votos.size();
        int totalVotosSim = (int) votos.stream()
                .filter(voto -> voto.getVoto().equalsIgnoreCase("Sim"))
                .count();
        int totalVotosNao = totalVotos - totalVotosSim;

        ResultadoVotacao resultadoVotacao = new ResultadoVotacao();
        resultadoVotacao.setPauta(pauta.get());
        resultadoVotacao.setTotalVotos(totalVotos);
        resultadoVotacao.setTotalVotosSim(totalVotosSim);
        resultadoVotacao.setTotalVotosNao(totalVotosNao);

        return  resultadoVotacao;

    }

    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }
}

