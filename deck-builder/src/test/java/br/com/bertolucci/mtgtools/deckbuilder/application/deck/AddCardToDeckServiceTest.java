package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.SaveService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legalities;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legality;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddCardToDeckServiceTest {

    @Mock
    private CollectionService collectionService;
    @Mock
    private SaveService saveService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(collectionService.getSaveService()).thenReturn(saveService);
    }

    @Test
    void testNullCard() {
        CardDeck cardDeck = new CardDeck(null, getDeck("standard"), 3);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, false);
        assertThrows(IllegalArgumentException.class, addCardToDeckService::add);
    }

    @Test
    void testNullDeck() {
        CardDeck cardDeck = new CardDeck(getCard(null, "a name"), null, 3);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, false);
        assertThrows(IllegalArgumentException.class, addCardToDeckService::add);
    }

    @Test
    void testNullLegalities() {
        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(new ArrayList<>());

        // legalities null
        CardDeck cardDeck = new CardDeck(getCard(null, "a name"), getDeck("standard"), 3);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, false);
        assertThrows(IllegalArgumentException.class, addCardToDeckService::add);

        // standard null
        Legalities legalities = new Legalities();
        legalities.setStandard(null);
        cardDeck = new CardDeck(getCard(legalities, "a name"), getDeck("standard"), 3);
        addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, false);
        assertThrows(IllegalArgumentException.class, addCardToDeckService::add);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 2, 3, 4, 5, 100})
    void testWrongQuantityInRestrictedCard(Integer input) {
        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(new ArrayList<>());

        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.RESTRICTED);
        CardDeck cardDeck = new CardDeck(getCard(legalities, "a name"), getDeck("standard"), input);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, false);
        assertThrows(IllegalArgumentException.class, addCardToDeckService::add);
    }

    @Test
    void testRestrictedCard() {
        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(new ArrayList<>());

        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.RESTRICTED);
        CardDeck cardDeck = new CardDeck(getCard(legalities, "a name"), getDeck("standard"), 1);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, false);

        assertDoesNotThrow(addCardToDeckService::add);
        Mockito.verify(collectionService.getSaveService()).save(Mockito.any());
    }

    @Test
    void testSameNameCard() {
        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.LEGAL);
        Card card = getCard(legalities, "a name");
        List<CardDeck> cardDeckList = new ArrayList<>();
        cardDeckList.add(new CardDeck(card, getDeck("standard"), 1));
        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(cardDeckList);

        CardDeck cardDeck = new CardDeck(getCard(legalities, "a name"), getDeck("standard"), 1);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, false);

        assertThrows(IllegalArgumentException.class, addCardToDeckService::add);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 5, 100})
    void testQuantities(Integer input) {
        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(new ArrayList<>());

        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.LEGAL);
        CardDeck cardDeck = new CardDeck(getCard(legalities, "a name"), getDeck("standard"), input);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, false);

        assertThrows(IllegalArgumentException.class, addCardToDeckService::add);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 100, 1000})
    void testRelentlessQuantities(Integer input) {
        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(new ArrayList<>());

        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.LEGAL);
        CardDeck cardDeck = new CardDeck(getCard(legalities, "a name"), getDeck("standard"), input);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, true);

        assertDoesNotThrow(addCardToDeckService::add);
        Mockito.verify(collectionService.getSaveService()).save(Mockito.any());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void testWrongRelentlessQuantities(Integer input) {
        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(new ArrayList<>());

        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.LEGAL);
        CardDeck cardDeck = new CardDeck(getCard(legalities, "a name"), getDeck("standard"), input);
        AddCardToDeckService addCardToDeckService = new AddCardToDeckService(collectionService, cardDeck, true);

        assertThrows(IllegalArgumentException.class, addCardToDeckService::add);
    }

    private Deck getDeck(String format) {
        return new Deck(format, "a name");
    }

    private Card getCard(Legalities legalities, String name) {
        Card card = new Card(null, "en", "A Type Line", "normal", "black", "missing", "200", name, 3.0, true, "common",
                "test.com");
        card.setLegalities(legalities);
        return card;
    }

}