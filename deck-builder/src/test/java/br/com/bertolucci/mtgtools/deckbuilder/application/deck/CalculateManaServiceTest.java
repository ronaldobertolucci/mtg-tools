package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.shared.card.Color;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculateManaServiceTest {

    @Test
    void testValue() {
        Deck deck = new DeckBuilder("Name test", "standard").build();

        // cmc value: 1
        insert(deck, "test0","{R}", 1.0);
        insert(deck, "test1","{B}", 1.0);
        insert(deck, "test2","{B}", 1.0);

        // cmc value: 2
        insert(deck, "test3","{2}", 2.0);
        insert(deck, "test4","{B}{B}", 2.0);
        insert(deck, "test5","{1}{B}", 2.0);
        insert(deck, "test6","{R}{B}", 2.0);
        insert(deck, "test7","{R}{R}", 2.0);
        insert(deck, "test8","{1}{R}", 2.0);

        // cmc value: 3
        insert(deck, "test9","{2}{R}", 3.0);
        insert(deck, "test10","{2}{B}", 3.0);
        insert(deck, "test11","{R}{R}{B}", 3.0);
        insert(deck, "test12","{B}{B}{B}", 3.0);
        insert(deck, "test13","{R}{R}{R}", 3.0);

        // cmc value: 4
        insert(deck, "test14", "{3}{R}", 4.0);
        insert(deck, "test15", "{3}{B}", 4.0);
        insert(deck, "test16", "{2}{R}{B}", 4.0);

        // cmc value: 5
        insert(deck, "test17", "{3}{B}{B}", 5.0);
        insert(deck, "test18", "{3}{R}{R}", 5.0);
        insert(deck, "test19", "{4}{B}", 5.0);

        // cmc value: 6
        insert(deck, "test20", "{5}{B}", 6.0);

        CalculateManaService cms = new CalculateManaService(0.92, deck);

        assertEquals(3.0, cms.getAvgCmc());
        assertEquals(12.88, cms.getSuggestedManaCards());
        assertEquals(6.04, cms.getResults().get(Color.R));
        assertEquals(6.84, cms.getResults().get(Color.B));
    }

    private void insert(Deck deck, String cardName, String manaCost, Double cardCmc) {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto(cardName, manaCost, cardCmc), createSet()).build(),
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

    private CardDto createCardDto(String name, String manaCost, Double cmc) {
        return new CardDto("123", "missing", "common", true, null,
                createLegalities("standard", "legal"), name, "1", "1",
                "type","1", "Oracle text test", manaCost, null,
                "ttt", cmc);
    }

    private Map<String, String> createLegalities(String format, String legality) {
        Map<String, String> legalities = new HashMap<>();
        legalities.put(format, legality);
        return legalities;
    }

}