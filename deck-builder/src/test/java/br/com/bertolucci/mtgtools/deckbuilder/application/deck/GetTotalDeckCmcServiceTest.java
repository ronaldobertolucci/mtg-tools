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

class GetTotalDeckCmcServiceTest {

    @Test
    void testValue() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        insert(deck, "test1", 1.0);
        insert(deck, "test2", 2.0);
        insert(deck, "test3", 10.0);
        insert(deck, "test4", 0.0);

        assertEquals(13.0, GetTotalDeckCmcService.getTotal(deck));
    }

    private void insert(Deck deck, String cardName, Double cardCmc) {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto(cardName, cardCmc), createSet()).build(),
                deck,
                1,
                false
        );
        new AddCardToDeckService(cardDeck, false).add();
    }

    private Set createSet() {
        Set set = new Set();
        set.setCode("ttt");
        return set;
    }

    private CardDto createCardDto(String name, Double cmc) {
        return new CardDto("123", "missing", "common", true, null,
                createLegalities("standard", "legal"), name, "1", "1",
                "Type line test","1", "Oracle text test", "{2}{B}", null,
                "ttt", cmc);
    }

    private Map<String, String> createLegalities(String format, String legality) {
        Map<String, String> legalities = new HashMap<>();
        legalities.put(format, legality);
        return legalities;
    }

}