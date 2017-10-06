package br.edu.utfpr.pb.projetojsp.specification;

import br.edu.utfpr.pb.projetojsp.model.RequerimentoDisciplina;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created by Edson on 05/10/2017.
 */
public class RequerimentoDisciplinaSpecification {
    public static Specification<RequerimentoDisciplina> withRequerimentoId(Long requerimentoId) {
        if (requerimentoId == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.join("requerimento").get("id"), requerimentoId);
        }
    }
}
