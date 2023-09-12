package br.com.bertolucci.mtgtools.deckbuilder.application;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface QueryService<T> {
    void and(Predicate predicate);

    <X> void addEqual(Expression<X> expression, X value);

    Root<T> getRoot();

    void setRoot(Root<T> root);

    CriteriaBuilder getCb();

    List<T> getResultList();

    T getSingleResult();
}
