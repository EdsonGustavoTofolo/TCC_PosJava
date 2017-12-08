package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Edson on 11/07/2017.
 */
public interface RequerimentoRepository extends JpaRepository<Requerimento, Long>, JpaSpecificationExecutor<Requerimento> {
    List<Requerimento> findByStatus(StatusRequerimentoEnum status);

    @Query(value = "select distinct r from Requerimento r " +
            "left join RequerimentoDisciplina rd on rd.requerimento.id=r.id " +
            "left join RequerimentoConvalidacao rc on rc.requerimento.id=r.id " +
            "join Disciplina d on d.id=coalesce(rd.disciplina.id, rc.disciplinaUtfpr.id) " +
            "join Curso c on c.id=d.curso.id join Usuario u on u.id=c.usuario.id where u.id=?1 and r.status=?2")
    List<Requerimento> findAllToCoordenacao(Long coordenacaoId, StatusRequerimentoEnum status);

    @Query(value = "select distinct r from Requerimento r join RequerimentoConvalidacao c on c.requerimento.id=r.id join Usuario u on u.id=c.professor.id where u.id=?1")
    List<Requerimento> findAllToProfessor(Long professorId);
}
