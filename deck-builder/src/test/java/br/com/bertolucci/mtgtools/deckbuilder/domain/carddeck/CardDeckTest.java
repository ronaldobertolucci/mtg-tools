package br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {

    private Card card;
    private Deck deck;
    private CardDeck cardDeckOne;

    @BeforeEach
    void setUp() {
        card = new Card(
                new Set("t", "t", "core", 100, true, "test.com", "2020-01-01"),
                "en",
                " test type",
                "normal",
                "black",
                "missing",
                "200",
                "test name ",
                0.0,
                true,
                "common",
                null);
        deck = new Deck("standard", "test");
        cardDeckOne = new CardDeck(card, deck, 1);
    }

    @Test
    void testCardDeckConstructor() {
        assertEquals(card, cardDeckOne.getCard());
        assertEquals(deck, cardDeckOne.getDeck());
        assertEquals(1, cardDeckOne.getQuantity());
    }

    @Test
    void testCardDeckEquals() {
        CardDeck cardDeckTwo = new CardDeck(card, deck, 1);
        assertNotSame(cardDeckOne, cardDeckTwo);
        assertEquals(cardDeckTwo, cardDeckTwo);
        assertNotEquals(cardDeckTwo, new Object());
        assertEquals(cardDeckOne, cardDeckTwo);
    }

    @Test
    void testCardDeckHashCode() {
        assertEquals(-1947980631, cardDeckOne.hashCode());
    }

    @Test
    void testCardDeckToString() {
        assertEquals("CardDeck{" +
                "card=test name" +
                ", deck=test: padrão (standard)" +
                ", quantity=1" +
                '}', cardDeckOne.toString());
    }

}