package br.com.jproject.appvoting.services;


import br.com.jproject.appvoting.dto.PautaDTO;
import br.com.jproject.appvoting.mapper.PautaMapperDTO;
import br.com.jproject.appvoting.mapper.VotoMapperDTO;
import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.repository.PautaRepository;
import br.com.jproject.appvoting.repository.SessaoVotacaoRepository;
import br.com.jproject.appvoting.repository.UsuarioRepository;
import br.com.jproject.appvoting.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PautaService {
        private final PautaRepository pautaRepository;
        private final PautaMapperDTO pautaMapperDTO;

    public PautaService(PautaRepository pautaRepository, PautaMapperDTO pautaMapperDTO, SessaoVotacaoRepository sessaoVotacaoRepository, UsuarioRepository usuarioRepository, VotoRepository votoRepository, VotoMapperDTO votoMapperDTO) {
        this.pautaRepository = pautaRepository;
        this.pautaMapperDTO = pautaMapperDTO;
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

    public List<Pauta> listarPautas() {
        return pautaRepository.findAll();
    }
}

