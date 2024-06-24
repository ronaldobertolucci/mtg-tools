package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

import br.com.bertolucci.mtgtools.deckbuilder.infra.AbstractJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class SetRepositoryImpl extends AbstractJpaRepository<Set> {

    public SetRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Optional<Set> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Set.class, id));
    }

    public List<Set> findAll() {
        String qlString = "SELECT s FROM Set s";
        TypedQuery<Set> query = entityManager.createQuery(qlString, Set.class);
        return query.getResultList();
    }

    public List<Set> findCoreSets() {
        String qlString = "SELECT s FROM Set s " +
                "WHERE s.type = 'CORE' " +
                "OR s.type = 'EXPANSION' " +
                "OR s.type = 'DRAFT_INNOVATION'";
        TypedQuery<Set> query = entityManager.createQuery(qlString, Set.class);
        return query.getResultList();
    }

    public List<Set> findTokenSets() {
        String qlString = "SELECT s FROM Set s " +
                "WHERE s.type = 'TOKEN' " +
                "AND s.name NOT LIKE '%Promo%' " +
                "AND s.parentSet IN " +
                "   (SELECT s.code from Set s " +
                "       WHERE s.type = 'CORE' " +
                "       OR s.type = 'EXPANSION' " +
                "       OR s.type = 'DRAFT_INNOVATION')";
        TypedQuery<Set> query = entityManager.createQuery(qlString, Set.class);
        return query.getResultList();
    }

}
