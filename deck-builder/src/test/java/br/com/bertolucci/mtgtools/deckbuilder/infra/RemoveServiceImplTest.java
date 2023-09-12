package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.application.RemoveService;
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

class RemoveServiceImplTest {

    @Mock
    private EntityManagerFactory emf;
    @Mock
    private Session em;
    @Mock
    private Transaction transaction;
    @Mock
    private RemoveService removeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(emf.createEntityManager()).thenReturn(em);
        Mockito.when(em.beginTransaction()).thenReturn(transaction);
        removeService = new RemoveServiceImpl(emf);
    }

    @Test
    void testRemove() {
        Deck deck = getDeck();
        Deck merged = getDeck();

        Mockito.when(em.merge(deck)).thenReturn(merged);
        removeService.remove(deck);

        Mockito.verify(em).merge(deck);
        Mockito.verify(em).remove(merged);
    }

    private Deck getDeck() {
        return new Deck("standard", "a name");
    }

}