package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Edson on 09/09/2017.
 */
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByCursoId(Long curso);
}
