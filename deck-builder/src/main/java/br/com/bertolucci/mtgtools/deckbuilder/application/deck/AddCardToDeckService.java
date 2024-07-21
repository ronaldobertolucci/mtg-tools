package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legality;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;

import java.util.NoSuchElementException;

public class AddCardToDeckService {

    private CardDeck cardDeck;
    private Boolean isRelentless;

    public AddCardToDeckService(CardDeck cardDeck, Boolean isRelentless) {
        this.cardDeck = cardDeck;
        this.isRelentless = isRelentless;
    }

    public void add() {
        if (cardDeck.getCard() == null || cardDeck.getDeck() == null) {
            throw new IllegalArgumentException("Deck ou card não podem ser nulos");
        }

        if (cardDeck.getIsSideboard() == null) {
            throw new IllegalArgumentException("Você deve definir se o card está no sideboard");
        }

        try {
            DeckFormat deckFormat = cardDeck.getDeck().getDeckFormat();
            Legality legality = cardDeck.getCard().getLegalities()
                    .stream().filter(cardLegality -> cardLegality.getDeckFormat() == deckFormat)
                    .findFirst().orElseThrow()
                    .getLegality();

            if (cardDeck.getQuantity() > 0
                    && (cardDeck.getCard().getTypeLine().toLowerCase().contains("Basic Land".toLowerCase())
                    || cardDeck.getCard().getTypeLine().toLowerCase().contains("Basic Snow Land".toLowerCase()))) {
                cardDeck.getDeck().addCard(cardDeck);
                return;
            }

            if (legality == Legality.RESTRICTED && cardDeck.getQuantity() == 1
                    && validateName(cardDeck.getCard().getName())) {
                cardDeck.getDeck().addCard(cardDeck);
                return;
            }

            if (legality == Legality.LEGAL && validateName(cardDeck.getCard().getName())
                    && validateQuantity(cardDeck.getCard().getName(), cardDeck.getQuantity())) {
                cardDeck.getDeck().addCard(cardDeck);
                return;
            }

        } catch (NoSuchElementException | NullPointerException e) {
            throw new IllegalArgumentException("Legalidade não foi encontrada");
        }

        throw new IllegalArgumentException("O card não é permitido nesse formato de deck");
    }

    private boolean validateQuantity(String name, Integer quantity) {
        return ValidateCardQuantity.isValid(cardDeck, isRelentless);
    }

    private boolean validateName(String name) {
        return ValidateCardName.isValidOnInsert(cardDeck);
    }


}