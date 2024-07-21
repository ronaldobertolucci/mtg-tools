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

class GetTotalCostByColorServiceTest {

    @Test
    void testValues() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        insert(deck, "test1", "{B}",1.0);
        insert(deck, "test2", "{1}{U/W}", 2.0);
        insert(deck, "test3", "{10}", 10.0);
        insert(deck, "test4", "{3}{R}", 4.0);
        insert(deck, "test5", "{C/G}", 1.0);

        assertEquals(1, GetTotalCostByColorService.getTotal(deck).get(Color.B));
        assertEquals(1, GetTotalCostByColorService.getTotal(deck).get(Color.U));
        assertEquals(1, GetTotalCostByColorService.getTotal(deck).get(Color.W));
        assertEquals(1, GetTotalCostByColorService.getTotal(deck).get(Color.R));
        assertEquals(1, GetTotalCostByColorService.getTotal(deck).get(Color.C));
        assertEquals(1, GetTotalCostByColorService.getTotal(deck).get(Color.G));
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
                "Type line test","1", "Oracle text test", manaCost, null,
                "ttt", cmc);
    }

    private Map<String, String> createLegalities(String format, String legality) {
        Map<String, String> legalities = new HashMap<>();
        legalities.put(format, legality);
        return legalities;
    }

}