package br.com.bertolucci.mtgtools.deckbuilder.domain.symbol;

import br.com.bertolucci.mtgtools.deckbuilder.infra.AbstractJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class SymbolRepositoryImpl extends AbstractJpaRepository<Symbol> {

    public SymbolRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Optional<Symbol> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Symbol.class, id));
    }

    public List<Symbol> findAll() {
        String qlString = "SELECT s FROM Symbol s";
        TypedQuery<Symbol> query = entityManager.createQuery(qlString, Symbol.class);
        return query.getResultList();
    }

}
