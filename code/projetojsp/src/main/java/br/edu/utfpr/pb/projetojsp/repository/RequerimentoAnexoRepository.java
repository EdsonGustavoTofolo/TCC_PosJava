package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.RequerimentoAnexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Edson on 20/09/2017.
 */
public interface RequerimentoAnexoRepository extends JpaRepository<RequerimentoAnexo, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM RequerimentoAnexo a WHERE a.id=?1")
    void deleteById(Long id);
}
