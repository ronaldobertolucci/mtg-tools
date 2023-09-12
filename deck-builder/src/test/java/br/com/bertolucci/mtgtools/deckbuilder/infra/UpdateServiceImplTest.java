package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.application.UpdateService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UpdateServiceImplTest {
    @Mock
    private EntityManagerFactory emf;
    @Mock
    private Session em;
    @Mock
    private Transaction transaction;
    @Mock
    private UpdateService updateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(emf.createEntityManager()).thenReturn(em);
        Mockito.when(em.beginTransaction()).thenReturn(transaction);
        updateService = new UpdateServiceImpl(emf);
    }

    @Test
    void testUpdate() {
        Deck deck = getDeck();

        updateService.update(deck);

        Mockito.verify(em).merge(deck);
    }

    private Deck getDeck() {
        return new Deck("standard", "a name");
    }
}