package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.application.SaveService;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SaveServiceImpl implements SaveService {

    private EntityManagerFactory emf;

    public SaveServiceImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public <T> void save(T t) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            Transaction transaction = em.beginTransaction();
            em.persist(em.merge(t));
            transaction.commit();
        }
    }

    @Override
    public <T> void saveAll(List<T> list) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            Transaction transaction = em.beginTransaction();

            for (T t: list) {
                em.persist(em.merge(t));
            }

            transaction.commit();
        }
    }

}
