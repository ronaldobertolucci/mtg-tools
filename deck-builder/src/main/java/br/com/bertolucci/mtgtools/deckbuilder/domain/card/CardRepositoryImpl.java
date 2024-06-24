package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.CardSearchParametersDto;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.QueryFromCardSearchParameters;
import br.com.bertolucci.mtgtools.deckbuilder.infra.AbstractJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.Optional;

public class CardRepositoryImpl extends AbstractJpaRepository<Card> {

    public CardRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Card> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Card.class, id));
    }

    @Override
    public List<Card> findAll() {
        String qlString = "SELECT c FROM Card c";
        TypedQuery<Card> query = entityManager.createQuery(qlString, Card.class);
        return query.getResultList();
    }

    public List<Card> findAllBySet(Integer setId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Card> cq = cb.createQuery(Card.class);
        Root<Card> root = cq.from(Card.class);
        root.fetch("set", JoinType.INNER);
        root.fetch("legalities", JoinType.INNER);
        root.fetch("faces", JoinType.LEFT);

        ParameterExpression<Integer> pSetId = cb.parameter(Integer.class);
        cq.where(cb.equal(root.join("set", JoinType.INNER).get("id"), pSetId));

        TypedQuery<Card> q = entityManager.createQuery(cq);
        q.setParameter(pSetId, setId);
        return q.getResultList();
    }

    public Long totalCardsBySet(Integer setId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Card> root = cq.from(Card.class);
        root.join("set", JoinType.INNER);

        ParameterExpression<Integer> pSetId = cb.parameter(Integer.class);
        cq.select(cb.count(root)).where(cb.equal(root.join("set", JoinType.INNER).get("id"), pSetId));

        TypedQuery<Long> q = entityManager.createQuery(cq);
        q.setParameter(pSetId, setId);
        return q.getSingleResult();
    }

    public List<Card> findWithParameters(CardSearchParametersDto cardParameters, int limit) {
        TypedQuery<Card> query = new QueryFromCardSearchParameters(entityManager, cardParameters, limit)
                .getQuery();
        return query.getResultList();
    }

}
