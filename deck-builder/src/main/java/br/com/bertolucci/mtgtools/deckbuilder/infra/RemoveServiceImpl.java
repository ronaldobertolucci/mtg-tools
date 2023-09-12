package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.application.RemoveService;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RemoveServiceImpl implements RemoveService {
    private EntityManagerFactory emf;

    public RemoveServiceImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public <T> void remove(T t) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            Transaction transaction = em.beginTransaction();
            em.remove(em.merge(t));
            transaction.commit();
        }
    }

}
