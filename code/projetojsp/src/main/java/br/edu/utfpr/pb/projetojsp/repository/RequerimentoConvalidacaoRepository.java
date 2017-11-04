package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.RequerimentoConvalidacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Edson on 04/11/2017.
 */
public interface RequerimentoConvalidacaoRepository extends JpaRepository<RequerimentoConvalidacao, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM RequerimentoConvalidacao c WHERE c.requerimento.id=?1")
    void deleteByRequerimentoId(Long requerimentoId);
}
