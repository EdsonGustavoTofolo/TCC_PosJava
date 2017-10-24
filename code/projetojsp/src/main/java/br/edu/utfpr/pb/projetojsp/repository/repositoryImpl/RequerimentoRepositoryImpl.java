package br.edu.utfpr.pb.projetojsp.repository.repositoryImpl;

import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Edson on 23/10/2017.
 */

@Repository
public class RequerimentoRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    public Page<Requerimento> findAllToCoordenacao(Specification<Requerimento> specs, PageRequest pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Requerimento> requerimentoCriteriaQuery = criteriaBuilder.createQuery(Requerimento.class);

        Root<Requerimento> requerimentoRoot = requerimentoCriteriaQuery.distinct(true).from(Requerimento.class);

        Predicate predicate = specs.toPredicate(requerimentoRoot, requerimentoCriteriaQuery, criteriaBuilder);

        Integer pageSize = pageable.getPageSize();

        Integer pageNo = pageable.getPageNumber();

        TypedQuery<Requerimento> typedQuery = em.createQuery(requerimentoCriteriaQuery.where(predicate));

        int startIndex = pageSize * pageNo;
        typedQuery.setFirstResult(startIndex);
        typedQuery.setMaxResults(pageable.getPageSize());

        TypedQuery<Requerimento> typedCountQuery = em.createQuery(requerimentoCriteriaQuery.where(predicate));

        Page<Requerimento> requerimentos = new PageImpl<>(typedQuery.getResultList(), pageable, typedCountQuery.getResultList().size());

        return requerimentos;
    }
}
