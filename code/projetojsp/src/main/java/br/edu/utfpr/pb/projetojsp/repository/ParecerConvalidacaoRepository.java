package br.edu.utfpr.pb.projetojsp.repository;

import br.edu.utfpr.pb.projetojsp.model.ParecerConvalidacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Edson on 12/11/2017.
 */
public interface ParecerConvalidacaoRepository extends JpaRepository<ParecerConvalidacao, Long> {
    ParecerConvalidacao findByConvalidacaoId(Long id);
}
