package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Edson on 15/09/2017.
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
