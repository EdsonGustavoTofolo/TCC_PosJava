package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Edson on 11/07/2017.
 */
public interface RequerimentoRepository extends JpaRepository<Requerimento, Long>, JpaSpecificationExecutor<Requerimento> {
    List<Requerimento> findByStatus(StatusRequerimentoEnum status);
    List<Requerimento> findByUsuarioIdAndStatusOrStatus(Long alunoId, StatusRequerimentoEnum status1, StatusRequerimentoEnum status2);
}
