package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;

import java.util.List;
import java.util.Objects;

public class ValidateCardName {

    public static boolean isValidOnInsert(CardDeck cardDeck) {
        List<CardDeck> sideboardCards = cardDeck.getDeck().getCards()
                .stream()
                .filter(CardDeck::getIsSideboard).toList();

        List<CardDeck> deckCards = cardDeck.getDeck().getCards()
                .stream()
                .filter(cd -> !cardDeck.getIsSideboard()).toList();

        if (cardDeck.getIsSideboard()) {
            sideboardCards.forEach(cd -> {
                if (cd.getIsSideboard() && cd.getCard().getName().equalsIgnoreCase(cardDeck.getCard().getName())) {
                    throw new IllegalArgumentException("O card selecionado já está no sideboard");
                }
            });
        } else {
            deckCards.forEach(cd -> {
                if (!cd.getIsSideboard() && cd.getCard().getName().equalsIgnoreCase(cardDeck.getCard().getName())) {
                    throw new IllegalArgumentException("O card selecionado já está no deck");
                }
            });
        }

        return true;
    }

    public static boolean isValidOnUpdate(CardDeck cardDeck) {
        List<CardDeck> sideboardCards = cardDeck.getDeck().getCards()
                .stream()
                .filter(CardDeck::getIsSideboard).toList();

        List<CardDeck> deckCards = cardDeck.getDeck().getCards()
                .stream()
                .filter(cd -> !cardDeck.getIsSideboard()).toList();

        sideboardCards.forEach(cd -> {
            if (cd.getCard().getName().equalsIgnoreCase(cardDeck.getCard().getName())
                    && !Objects.equals(cardDeck.getId(), cd.getId())) {
                throw new IllegalArgumentException("O card selecionado já está no sideboard");
            }
        });
        deckCards.forEach(cd -> {
            if (cd.getCard().getName().equalsIgnoreCase(cardDeck.getCard().getName())
                    && !Objects.equals(cardDeck.getId(), cd.getId())) {
                throw new IllegalArgumentException("O card selecionado já está no deck");
            }
        });

        return true;
    }
}
