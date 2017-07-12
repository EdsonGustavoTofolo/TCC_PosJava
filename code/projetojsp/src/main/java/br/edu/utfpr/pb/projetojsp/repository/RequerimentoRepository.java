package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Edson on 11/07/2017.
 */
public interface RequerimentoRepository extends JpaRepository<Requerimento, Long> {
}
