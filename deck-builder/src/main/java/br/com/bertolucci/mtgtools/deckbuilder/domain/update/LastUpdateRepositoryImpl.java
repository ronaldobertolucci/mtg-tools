package br.com.bertolucci.mtgtools.deckbuilder.domain.update;

import br.com.bertolucci.mtgtools.deckbuilder.infra.AbstractJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LastUpdateRepositoryImpl extends AbstractJpaRepository<LastUpdate> {

    public LastUpdateRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Optional<LastUpdate> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(LastUpdate.class, id));
    }

    @Override
    public List<LastUpdate> findAll() {
        String qlString = "SELECT u FROM LastUpdate u";
        TypedQuery<LastUpdate> query = entityManager.createQuery(qlString, LastUpdate.class);
        return query.getResultList();
    }

    public LocalDate findLastUpdate() {
        String qlString = "SELECT u FROM LastUpdate u ORDER BY u.date DESC";
        TypedQuery<LastUpdate> query = entityManager.createQuery(qlString, LastUpdate.class);

        try {
            return query.getResultList().stream().findFirst().orElseThrow().getDate();
        } catch (Exception e) {
            return null;
        }
    }
}
