package br.com.bertolucci.mtgtools.deckbuilder.application.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legality;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;

import java.util.List;

public class VerifyCardDeckLegalitiesService {

    private CollectionService collectionService;
    private Deck deck;

    public VerifyCardDeckLegalitiesService(CollectionService collectionService, Deck deck) {
        this.collectionService = collectionService;
        this.deck = deck;
    }

    public void run() {
        List<CardDeck> cardDeckList = collectionService.findCardDeckByDeck(deck.getId());

        cardDeckList.forEach(cardDeck -> {
            try {
                if (!(GetCardDeckLegalityService.get(cardDeck.getCard(), cardDeck.getDeck()) == Legality.RESTRICTED ||
                        GetCardDeckLegalityService.get(cardDeck.getCard(), cardDeck.getDeck()) == Legality.LEGAL)) {

                    collectionService.getRemoveService().remove(cardDeck);

                }
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        });

    }
}
