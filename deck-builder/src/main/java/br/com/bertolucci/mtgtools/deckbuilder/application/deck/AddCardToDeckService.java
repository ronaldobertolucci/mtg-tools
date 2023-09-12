package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.carddeck.GetCardDeckLegalityService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legality;

import java.util.List;

public class AddCardToDeckService {

    private CollectionService collectionService;
    private CardDeck cardDeck;
    private Boolean isRelentless;

    public AddCardToDeckService(CollectionService collectionService, CardDeck cardDeck, Boolean isRelentless) {
        this.collectionService = collectionService;
        this.cardDeck = cardDeck;
        this.isRelentless = isRelentless;
    }

    public boolean add() {
        if (cardDeck.getCard() == null || cardDeck.getDeck() == null) {
            throw new IllegalArgumentException("O deck ou o card não podem ser nulos");
        }

        try {
            if (GetCardDeckLegalityService.get(cardDeck.getCard(), cardDeck.getDeck()) == Legality.RESTRICTED
                    && cardDeck.getQuantity() == 1
                    && validateName(cardDeck.getCard().getName())
            ) {
                collectionService.getSaveService().save(cardDeck);
                return true;
            }

            if (GetCardDeckLegalityService.get(cardDeck.getCard(), cardDeck.getDeck()) == Legality.LEGAL
                    && validateQuantity(cardDeck.getQuantity())
                    && validateName(cardDeck.getCard().getName()
            )) {
                collectionService.getSaveService().save(cardDeck);
                return true;
            }
        } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
            throw new IllegalArgumentException("A legalidade do card não foi encontrada");
        }

        throw new IllegalArgumentException("O card não é permitido no formato do deck");
    }

    private boolean validateQuantity(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        if (!isRelentless && quantity > 4) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        return true;
    }

    private boolean validateName(String name) {
        List<CardDeck> cardDecks = collectionService.findCardDeckByDeck(cardDeck.getDeck().getId());

        cardDecks.forEach(cd -> {
            if (cd.getCard().getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException("O card já está no deck");
            }
        });

        return true;
    }


}
