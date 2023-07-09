package br.com.jproject.appvoting.repository;

import br.com.jproject.appvoting.model.Pauta;
import br.com.jproject.appvoting.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Voto v WHERE v.usuario.usuario_id = :usuarioId AND v.pauta.id = :pautaId")
    boolean existsByUsuarioIdAndPautaId (Long usuarioId, Long pautaId);
    List<Voto> findByPauta(Pauta pauta);
}
