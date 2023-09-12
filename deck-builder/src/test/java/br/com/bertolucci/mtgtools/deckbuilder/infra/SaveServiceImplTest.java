package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class SaveServiceImplTest {

    @Mock
    private EntityManagerFactory emf;
    @Mock
    private Session em;
    @Mock
    private Transaction transaction;
    @Mock
    private SaveServiceImpl saveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(emf.createEntityManager()).thenReturn(em);
        Mockito.when(em.beginTransaction()).thenReturn(transaction);
        saveService = new SaveServiceImpl(emf);
    }

    @Test
    void testSave() {
        Deck deck = getDeck();
        Mockito.when(em.merge(Mockito.any(Deck.class))).thenReturn(deck);

        saveService.save(new Deck("standard", "a name"));

        Mockito.verify(em).persist(deck);
    }

    private Deck getDeck() {
        return new Deck("standard", "a name");
    }

}