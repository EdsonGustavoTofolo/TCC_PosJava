package br.edu.utfpr.pb.projetojsp.specification;

import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

/**
 * Created by Edson on 27/09/2017.
 */
public class RequerimentoSpecification {
    public static Specification<Requerimento> withData(Date data) {
        if (data == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("data"), data);
        }
    }

    public static Specification<Requerimento> withId(Long id) {
        if (id == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.get("id"), id);
        }
    }

    public static Specification<Requerimento> withStatus(StatusRequerimentoEnum status) {
        if (status == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.get("status"), status);
        }
    }

    public static Specification<Requerimento> withObservacao(String observacao) {
        if (observacao == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.like(root.get("observacao"), "%" + observacao + "%");
        }
    }

    public static Specification<Requerimento> withAlunoNome(String alunoNome) {
        if (alunoNome == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.like(root.join("usuario").get("nome"), "%" + alunoNome + "%");
        }
    }

    public static Specification<Requerimento> withMotivo(Integer motivo) {
        if (motivo == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.get("motivo"), motivo);
        }
    }

    public static Specification<Requerimento> withUsuarioId(Long usuarioId) {
        if (usuarioId == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.join("usuario").get("id"), usuarioId);
        }
    }

    public static Specification<Requerimento> withProfessorId(Long professorId) {
        if (professorId == null) {
            return null;
        } else {
            return (root, query, cb) -> cb.equal(root.join("disciplinas")
                                                    .join("professor").get("id"), professorId);
        }
    }
}
