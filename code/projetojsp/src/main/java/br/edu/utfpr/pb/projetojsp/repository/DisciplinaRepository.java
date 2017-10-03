package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * Created by Edson on 09/09/2017.
 */
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByCursoId(Long curso);

    List<Disciplina> findByCursoIdAndIdNotIn(Long cursoId, List<Long> disciplinasId);

    @Query(value = "select d from RequerimentoDisciplina r join Disciplina d on d.id=r.disciplina.id where r.requerimento.id = ?1")
    List<Disciplina> findByRequerimentoId(Long requerimentoId);
}
