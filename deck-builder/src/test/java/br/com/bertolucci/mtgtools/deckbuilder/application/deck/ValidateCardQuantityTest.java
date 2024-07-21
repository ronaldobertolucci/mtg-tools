package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

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

class ValidateCardQuantityTest {

    @Test
    void testMaxSideboardCards() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("1"), createSet()).build(),
                deck,
                4,
                true
        );
        new AddCardToDeckService(cardDeck1, false).add();

        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("2"), createSet()).build(),
                deck,
                4,
                true
        );
        new AddCardToDeckService(cardDeck2, false).add();

        CardDeck cardDeck3 = new CardDeck(
                new CardBuilder(createCardDto("3"), createSet()).build(),
                deck,
                4,
                true
        );
        new AddCardToDeckService(cardDeck3, false).add();

        CardDeck cardDeck4 = new CardDeck(
                new CardBuilder(createCardDto("4"), createSet()).build(),
                deck,
                3,
                true
        );

        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck4, false).add());
    }

    @Test
    void throwsMaxSideboardCards() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("1"), createSet()).build(),
                deck,
                4,
                true
        );
        new AddCardToDeckService(cardDeck1, false).add();

        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("2"), createSet()).build(),
                deck,
                4,
                true
        );
        new AddCardToDeckService(cardDeck2, false).add();

        CardDeck cardDeck3 = new CardDeck(
                new CardBuilder(createCardDto("3"), createSet()).build(),
                deck,
                4,
                true
        );
        new AddCardToDeckService(cardDeck3, false).add();

        CardDeck cardDeck4 = new CardDeck(
                new CardBuilder(createCardDto("4"), createSet()).build(),
                deck,
                4,
                true
        );

        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck4, false).add());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testInsertWithValidSideboardQuantities(Integer input) {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                input,
                false
        );
        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                true
        );
        new AddCardToDeckService(cardDeck1, false).add();

        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck2, false).add());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testInsertWithValidDeckQuantities(Integer input) {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                false
        );
        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                input,
                true
        );
        new AddCardToDeckService(cardDeck1, false).add();

        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck2, false).add());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 4, 100})
    void throwsInsertWithInvalidSideboardQuantities(Integer input) {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                false
        );
        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                input,
                true
        );
        new AddCardToDeckService(cardDeck1, false).add();

        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck2, false).add());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 4, 100})
    void throwsInsertWithInvalidDeckQuantities(Integer input) {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                false
        );
        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                input,
                true
        );
        new AddCardToDeckService(cardDeck1, false).add();

        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck2, false).add());
    }

    private Set createSet() {
        Set set = new Set();
        set.setCode("ttt");
        return set;
    }

    private CardDto createCardDto(String name) {
        return new CardDto("123", "missing", "common", true, null,
                createLegalities("standard", "legal"), name, "1", "1",
                "Type line test","1", "Oracle text test", "{2}{B}", null,
                "ttt", 3.0);
    }

    private Map<String, String> createLegalities(String format, String legality) {
        Map<String, String> legalities = new HashMap<>();
        legalities.put(format, legality);
        return legalities;
    }

}