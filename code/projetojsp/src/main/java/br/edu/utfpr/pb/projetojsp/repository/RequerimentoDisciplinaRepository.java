package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.RequerimentoDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Edson on 11/07/2017.
 */
public interface RequerimentoDisciplinaRepository extends JpaRepository<RequerimentoDisciplina, Long> {
    List<RequerimentoDisciplina> findByRequerimentoId(Long requerimentoId);
}
