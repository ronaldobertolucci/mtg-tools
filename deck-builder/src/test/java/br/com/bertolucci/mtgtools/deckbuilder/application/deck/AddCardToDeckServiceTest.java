package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AddCardToDeckServiceTest {

    private Card card;

    @Test
    void throwsExceptionWhenCardIsNull() {
        CardDeck cardDeck = new CardDeck(null, new Deck(), 1);
        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck, false).add());
    }

    @Test
    void throwsExceptionWhenDeckIsNull() {
        CardDeck cardDeck = new CardDeck(new Card(), null, 1);
        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck, false).add());
    }

    @Test
    void throwsExceptionWhenCardAlreadyInDeck() {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("standard", "legal"), createSet()).build(),
                new DeckBuilder("Name test", "standard").build(),
                1
        );
        new AddCardToDeckService(cardDeck, false).add();

        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck, false).add());
    }

    @Test
    void throwsExceptionWhenLegalityNotFound() {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("standard", "legal"), createSet()).build(),
                new DeckBuilder("Name test", "historic").build(),
                1
        );

        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck, false).add());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 5})
    void testWrongQuantities(Integer input) {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("standard", "legal"), createSet()).build(),
                new DeckBuilder("Name test", "standard").build(),
                input
        );

        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck, false).add());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 100})
    void testRelentlessQuantities(Integer input) {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("standard", "legal"), createSet()).build(),
                new DeckBuilder("Name test", "standard").build(),
                input
        );

        assertTrue(cardDeck.getDeck().getCards().isEmpty());
        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck, true).add());
        assertFalse(cardDeck.getDeck().getCards().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 2, 3, 4, 100})
    void throwsExceptionWhenCardIsRestricted(Integer input) {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("standard", "restricted"), createSet()).build(),
                new DeckBuilder("Name test", "standard").build(),
                input
        );

        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck, false).add());
    }

    @Test
    void testRestricted() {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("standard", "restricted"), createSet()).build(),
                new DeckBuilder("Name test", "standard").build(),
                1
        );

        assertTrue(cardDeck.getDeck().getCards().isEmpty());
        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck, true).add());
        assertFalse(cardDeck.getDeck().getCards().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 100})
    void testBasicLands(Integer input) {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("standard", "legal"), createSet())
                        .setTypeLine("Basic Land")
                        .build(),
                new DeckBuilder("Name test", "standard").build(),
                input
        );

        assertTrue(cardDeck.getDeck().getCards().isEmpty());
        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck, true).add());
        assertFalse(cardDeck.getDeck().getCards().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 100})
    void testBasicSnowLands(Integer input) {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("standard", "legal"), createSet())
                        .setTypeLine("Basic Snow Land")
                        .build(),
                new DeckBuilder("Name test", "standard").build(),
                input
        );

        assertTrue(cardDeck.getDeck().getCards().isEmpty());
        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck, true).add());
        assertFalse(cardDeck.getDeck().getCards().isEmpty());
    }

    private Set createSet() {
        Set set = new Set();
        set.setCode("ttt");
        return set;
    }

    private CardDto createCardDto(String format, String legality) {
        return new CardDto("123", "missing", "common", true, null,
                createLegalities(format, legality), "Name test", "1", "1",
                "Type line test","1", "Oracle text test", "{2}{B}", null,
                "ttt", 3.0);
    }

    private Map<String, String> createLegalities(String format, String legality) {
        Map<String, String> legalities = new HashMap<>();
        legalities.put(format, legality);
        return legalities;
    }

}