package br.com.jproject.appvoting.repository;

import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

    boolean existsByPautaAndDataEncerramentoIsNull(Pauta pauta);

}
