package br.com.bertolucci.mtgtools.deckbuilder.infra.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("default");
    private static EntityManager entityManager;

    public static EntityManagerFactory getFactory() {
        return factory;
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
