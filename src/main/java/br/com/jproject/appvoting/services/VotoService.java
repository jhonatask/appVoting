package br.com.jproject.appvoting.services;

import br.com.jproject.appvoting.dto.VotoDTO;
import br.com.jproject.appvoting.mapper.VotoMapperDTO;
import br.com.jproject.appvoting.model.*;
import br.com.jproject.appvoting.repository.PautaRepository;
import br.com.jproject.appvoting.repository.UsuarioRepository;
import br.com.jproject.appvoting.repository.VotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotoService {
    private final UsuarioRepository usuarioRepository;
    private final PautaRepository pautaRepository;
    private final VotoMapperDTO votoMapperDTO;
    private final VotoRepository votoRepository;

    public VotoService(UsuarioRepository usuarioRepository, PautaRepository pautaRepository, VotoMapperDTO votoMapperDTO, VotoRepository votoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pautaRepository = pautaRepository;
        this.votoMapperDTO = votoMapperDTO;
        this.votoRepository = votoRepository;
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

    public ResultadoVotacao obterResultadoVotacao(Long id) {

        Optional<Pauta> pauta = pautaRepository.findById(id);

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
}
