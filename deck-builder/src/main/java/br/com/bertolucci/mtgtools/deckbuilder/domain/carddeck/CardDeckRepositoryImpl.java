package br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.infra.AbstractJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CardDeckRepositoryImpl extends AbstractJpaRepository<CardDeck> {

    public CardDeckRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<CardDeck> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(CardDeck.class, id));
    }

    @Override
    public List<CardDeck> findAll() {
        String qlString = "SELECT cd FROM CardDeck cd";
        TypedQuery<CardDeck> query = entityManager.createQuery(qlString, CardDeck.class);
        return query.getResultList();
    }
}
