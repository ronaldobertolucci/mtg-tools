package br.com.bertolucci.mtgtools.deckbuilder.domain.deck;

import br.com.bertolucci.mtgtools.deckbuilder.infra.AbstractJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class DeckRepositoryImpl extends AbstractJpaRepository<Deck> {

    public DeckRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<Deck> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Deck.class, id));
    }

    @Override
    public List<Deck> findAll() {
        String qlString = "SELECT d FROM Deck d";
        TypedQuery<Deck> query = entityManager.createQuery(qlString, Deck.class);
        return query.getResultList();
    }
}
