package br.com.bertolucci.mtgtools.deckbuilder.infra;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public abstract class AbstractJpaRepository<T> implements Repository<T> {

    protected EntityManager entityManager;

    public AbstractJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract Optional<T> findById(Integer id);

    @Override
    public T save(T t) {
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        return t;
    }

    @Override
    public void saveAll(List<T> tList) {
        entityManager.getTransaction().begin();

        for (int i = 0; i < tList.size(); i++) {
            entityManager.persist(tList.get(i));

            if (i % 20 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        entityManager.getTransaction().commit();
    }

    @Override
    public void updateAll(List<T> tList) {
        entityManager.getTransaction().begin();

        for (int i = 0; i < tList.size(); i++) {
            entityManager.merge(tList.get(i));

            if (i % 20 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        entityManager.getTransaction().commit();
    }

    @Override
    public void removeAll(List<T> tList) {
        entityManager.getTransaction().begin();

        for (int i = 0; i < tList.size(); i++) {
            entityManager.remove(entityManager.merge(tList.get(i)));

            if (i % 20 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        entityManager.getTransaction().commit();
    }

    @Override
    public void removeById(Integer id) {
        entityManager.getTransaction().begin();
        findById(id).ifPresent(entityManager::remove);
        entityManager.getTransaction().commit();
    }

    public abstract List<T> findAll();

    @Override
    public T update(T t) {
        entityManager.getTransaction().begin();
        T updatedT = entityManager.merge(t);
        entityManager.getTransaction().commit();
        return updatedT;
    }

}
