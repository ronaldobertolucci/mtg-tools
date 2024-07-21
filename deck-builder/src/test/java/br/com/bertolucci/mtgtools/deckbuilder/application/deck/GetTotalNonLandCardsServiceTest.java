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

class GetTotalNonLandCardsServiceTest {

    @Test
    void testValue() {
        Deck deck = new DeckBuilder("Name test", "standard").build();
        insert(deck, "Creature", "test1", 1.0);
        insert(deck, "basiC land","test2", 0.0);
        insert(deck, "Basic Snow Land", "test3", 0.0);
        insert(deck, "Spell", "test4", 1.0);
        insert(deck, "Artefato", "test5", 0.0);

        assertEquals(3, GetTotalNonLandCardsService.getTotal(deck));
    }

    private void insert(Deck deck, String cardType, String cardName, Double cardCmc) {
        CardDeck cardDeck = new CardDeck(
                new CardBuilder(createCardDto(cardName, cardType, cardCmc), createSet()).build(),
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

    private CardDto createCardDto(String name, String type, Double cmc) {
        return new CardDto("123", "missing", "common", true, null,
                createLegalities("standard", "legal"), name, "1", "1",
                type,"1", "Oracle text test", "{2}{B}", null,
                "ttt", cmc);
    }

    private Map<String, String> createLegalities(String format, String legality) {
        Map<String, String> legalities = new HashMap<>();
        legalities.put(format, legality);
        return legalities;
    }

}