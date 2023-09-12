package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.application.UpdateService;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UpdateServiceImpl implements UpdateService {

    private EntityManagerFactory emf;

    public UpdateServiceImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public <T> void update(T t) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            Transaction transaction = em.beginTransaction();
            em.merge(t);
            transaction.commit();
        }
    }
}
