package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.RemoveService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legalities;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legality;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerifyCardDeckLegalitiesServiceTest {

    @Mock
    private CollectionService collectionService;
    @Mock
    private RemoveService removeService;
    private VerifyCardDeckLegalitiesService verifyCardDeckLegalitiesService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(collectionService.getRemoveService()).thenReturn(removeService);
    }

    @Test
    void testRestrictedCardWithCorrectDeckFormat() {
        Deck standardDeck = getDeck("standard");

        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.RESTRICTED);

        List<CardDeck> cardDecks = new ArrayList<>();
        cardDecks.add(new CardDeck(getCard(legalities), standardDeck));

        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(cardDecks);

        verifyCardDeckLegalitiesService = new VerifyCardDeckLegalitiesService(collectionService, standardDeck);
        verifyCardDeckLegalitiesService.run();

        Mockito.verifyNoInteractions(collectionService.getRemoveService());
    }

    @Test
    void testLegalCardWithCorrectDeckFormat() {
        Deck standardDeck = getDeck("standard");

        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.LEGAL);

        List<CardDeck> cardDecks = new ArrayList<>();
        cardDecks.add(new CardDeck(getCard(legalities), standardDeck));

        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(cardDecks);

        verifyCardDeckLegalitiesService = new VerifyCardDeckLegalitiesService(collectionService, standardDeck);
        verifyCardDeckLegalitiesService.run();

        Mockito.verifyNoInteractions(collectionService.getRemoveService());
    }

    @Test
    void testIncorrectDeckFormat() {
        Deck modernDeck = getDeck("modern");

        Legalities legalities = new Legalities();
        legalities.setStandard(Legality.LEGAL);
        legalities.setModern(Legality.NOT_LEGAL);

        List<CardDeck> cardDecks = new ArrayList<>();
        cardDecks.add(new CardDeck(getCard(legalities), modernDeck));

        Mockito.when(collectionService.findCardDeckByDeck(Mockito.any())).thenReturn(cardDecks);

        verifyCardDeckLegalitiesService = new VerifyCardDeckLegalitiesService(collectionService, modernDeck);
        verifyCardDeckLegalitiesService.run();

        Mockito.verify(collectionService.getRemoveService()).remove(Mockito.any(CardDeck.class));
    }

    private Deck getDeck(String format) {
        return new Deck(format, "a name");
    }

    private Card getCard(Legalities legalities) {
        Card card = new Card(null, "en", "A Type Line", "normal", "black", "missing", "200", "a name", 3.0,
                true, "common", "test.com");
        card.setLegalities(legalities);
        return card;
    }

}