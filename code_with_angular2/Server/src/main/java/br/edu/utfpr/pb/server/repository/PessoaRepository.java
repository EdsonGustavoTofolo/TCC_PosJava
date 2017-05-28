package br.edu.utfpr.pb.server.repository;

import br.edu.utfpr.pb.server.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Edson on 05/03/2017.
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
