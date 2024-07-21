package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidateCardNameTest {

    @Test
    void testInsertWithNoSideboardCards() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                false
        );
        CardDeck sideboardCardDeck = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                true
        );

        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck, false).add());
        assertDoesNotThrow(() -> new AddCardToDeckService(sideboardCardDeck, false).add());
    }

    @Test
    void throwsInsertWithSideboardCards() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                true
        );
        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                true
        );

        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck1, false).add());
        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck2, false).add());
    }

    @Test
    void throwsInsertWithDeckCards() {
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
                1,
                false
        );

        assertDoesNotThrow(() -> new AddCardToDeckService(cardDeck1, false).add());
        assertThrows(Exception.class, () -> new AddCardToDeckService(cardDeck2, false).add());
    }

    @Test
    void testUpdateWithNoSideboardCards() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                false
        );
        cardDeck.setIsSideboard(true);
        new AddCardToDeckService(cardDeck, false).add();

        assertDoesNotThrow(() -> ValidateCardName.isValidOnUpdate(cardDeck));
    }

    @Test
    void testUpdateWithNoDeckCards() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                true
        );
        cardDeck.setIsSideboard(false);
        new AddCardToDeckService(cardDeck, false).add();

        assertDoesNotThrow(() -> ValidateCardName.isValidOnUpdate(cardDeck));
    }

    @Test
    void throwsUpdateWithSideboardCards() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                false
        );
        cardDeck1.setId(1);
        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                true
        );
        cardDeck2.setId(2);
        new AddCardToDeckService(cardDeck1, false).add();
        new AddCardToDeckService(cardDeck2, false).add();

        cardDeck1.setIsSideboard(true);
        assertThrows(Exception.class, () -> ValidateCardName.isValidOnUpdate(cardDeck1));
    }

    @Test
    void throwsUpdateWithDeckCards() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        CardDeck cardDeck1 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                true
        );
        cardDeck1.setId(1);
        CardDeck cardDeck2 = new CardDeck(
                new CardBuilder(createCardDto("test"), createSet()).build(),
                deck,
                1,
                false
        );
        cardDeck2.setId(2);
        new AddCardToDeckService(cardDeck1, false).add();
        new AddCardToDeckService(cardDeck2, false).add();

        cardDeck1.setIsSideboard(false);
        assertThrows(Exception.class, () -> ValidateCardName.isValidOnUpdate(cardDeck1));
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