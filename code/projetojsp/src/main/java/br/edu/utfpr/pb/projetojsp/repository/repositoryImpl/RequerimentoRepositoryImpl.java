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
import javax.persistence.criteria.*;
import javax.transaction.Transactional;

/**
 * Created by Edson on 23/10/2017.
 */

@Repository
public class RequerimentoRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    public Page<Requerimento> findAllWithDistinct(Specification<Requerimento> specs, PageRequest pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Requerimento> requerimentoCriteriaQuery = criteriaBuilder.createQuery(Requerimento.class);
        Root<Requerimento> requerimentoRoot = requerimentoCriteriaQuery.distinct(true).from(Requerimento.class);

        Predicate predicate = specs.toPredicate(requerimentoRoot, requerimentoCriteriaQuery, criteriaBuilder);

        TypedQuery<Requerimento> typedQuery = em.createQuery(requerimentoCriteriaQuery.where(predicate));

        Integer pageSize = pageable.getPageSize();

        Integer pageNo = pageable.getPageNumber();

        int startIndex = pageSize * pageNo;
        typedQuery.setFirstResult(startIndex);
        typedQuery.setMaxResults(pageable.getPageSize());

        Page<Requerimento> requerimentos = new PageImpl<>(typedQuery.getResultList(), pageable, typedQuery.getResultList().size());

        return requerimentos;
    }

    @Transactional
    public Long count(Specification<Requerimento> specs) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaQuery<Long> queryCount = criteriaBuilder.createQuery(Long.class);

        Root<Requerimento> requerimentoRoot = queryCount.from(Requerimento.class);

        Subquery<Requerimento> subquery = queryCount.subquery(Requerimento.class);

        Root<Requerimento> subqueryRoot = subquery.from(Requerimento.class);

        subquery.select(subqueryRoot).distinct(true).where(specs.toPredicate(subqueryRoot, queryCount, criteriaBuilder));

        queryCount.select(criteriaBuilder.count(requerimentoRoot)).where(criteriaBuilder.in(requerimentoRoot).value(subquery));

        Long count = em.createQuery(queryCount).getSingleResult();

        return count;
    }
}
