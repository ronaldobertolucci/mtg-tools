package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.application.QueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;

import java.util.List;

public class QueryServiceImpl<T> implements QueryService<T> {

    private EntityManager em;
    private Class<T> entityClass;
    private CriteriaQuery<T> query;
    private Root<T> root;
    private Predicate filter;
    private CriteriaBuilder cb;

    public QueryServiceImpl(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;

        this.cb = em.unwrap(Session.class).getCriteriaBuilder();
        this.query = cb.createQuery(entityClass);
        this.root = query.from(entityClass);
        this.filter = cb.and();
    }

    @Override
    public void and(Predicate predicate) {
        filter = cb.and(filter, predicate);
    }

    @Override
    public <X> void addEqual(Expression<X> expression, X value) {
        if (expression != null && value != null && cb != null)
            and(cb.equal(expression, value));
    }

    @Override
    public Root<T> getRoot() {
        return root;
    }

    @Override
    public void setRoot(Root<T> root) {
        this.root = root;
    }

    @Override
    public CriteriaBuilder getCb() {
        return cb;
    }

    @Override
    public List<T> getResultList() {
        return em.createQuery(query.where(filter)).getResultList();
    }

    @Override
    public T getSingleResult() {
        return em.createQuery(query.where(filter)).getSingleResult();
    }
}
