package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CheckLegalitiesServiceTest {

    @Mock
    private DeckBuilderService deckBuilderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHasIllegal() {
        Deck deck = createDeck(DeckFormat.STANDARD, "Teste");
        addCardToDeck(new CardBuilder(createCardDto("standard", "legal"), new Set()).build(), deck);
        addCardToDeck(new CardBuilder(createCardDto("standard", "not_legal"), new Set()).build(), deck);

        CheckLegalitiesService checkLegalitiesService = new CheckLegalitiesService(deck, deckBuilderService);

        assertTrue(checkLegalitiesService.hasIllegal());
    }

    @Test
    public void testHasNoIllegal() {
        Deck deck = createDeck(DeckFormat.STANDARD, "Teste");
        addCardToDeck(new CardBuilder(createCardDto("standard", "legal"), new Set()).build(), deck);
        addCardToDeck(new CardBuilder(createCardDto("standard", "restricted"), new Set()).build(), deck);

        CheckLegalitiesService checkLegalitiesService = new CheckLegalitiesService(deck, deckBuilderService);

        assertFalse(checkLegalitiesService.hasIllegal());
    }

    @Test
    public void testRemoveIllegalCards() {
        Deck deck = createDeck(DeckFormat.STANDARD, "Teste");
        addCardToDeck(new CardBuilder(createCardDto("standard", "legal"), new Set()).build(), deck);
        addCardToDeck(new CardBuilder(createCardDto("standard", "restricted"), new Set()).build(), deck);
        addCardToDeck(new CardBuilder(createCardDto("standard", "banned"), new Set()).build(), deck);
        addCardToDeck(new CardBuilder(createCardDto("standard", "not_legal"), new Set()).build(), deck);

        CheckLegalitiesService checkLegalitiesService = new CheckLegalitiesService(deck, deckBuilderService);
        checkLegalitiesService.removeIllegalCards();

        Mockito.verify(deckBuilderService, Mockito.times(2)).removeCardFromDeck(Mockito.any());
    }

    private void addCardToDeck(Card card, Deck deck) {
        CardDeck cardDeck = new CardDeck(card, deck);
        deck.addCard(cardDeck);
    }

    private Deck createDeck(DeckFormat format, String name) {
        Deck deck = new Deck();
        deck.setName(name);
        deck.setDeckFormat(format);
        return deck;
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