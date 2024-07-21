package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ValidateCardQuantity {

    public static boolean isValid(CardDeck cardDeck, Boolean isRelentless) {
        if (cardDeck.getQuantity() <= 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa ou zero");
        }

        AtomicInteger total = new AtomicInteger(cardDeck.getQuantity());
        if (cardDeck.getIsSideboard()) {
            cardDeck.getDeck().getCards().stream().filter(CardDeck::getIsSideboard).toList().forEach(cd -> {
                total.addAndGet(cd.getQuantity());
            });

            if (total.get() > 15) {
                throw new IllegalArgumentException("O sideboard não pode ter mais do que 15 cards");
            }
        }

        AtomicInteger totalWithSameName = new AtomicInteger(cardDeck.getQuantity());
        List<CardDeck> cards;

        if (cardDeck.getIsSideboard()) {
            cards = cardDeck.getDeck().getCards().stream().filter(cd -> !cd.getIsSideboard()).toList();
            cards.forEach(cd -> {
                if (cd.getCard().getName().equalsIgnoreCase(cardDeck.getCard().getName())) {
                    totalWithSameName.addAndGet(cd.getQuantity());
                }
            });
        } else {
            cards = cardDeck.getDeck().getCards().stream().filter(CardDeck::getIsSideboard).toList();
            cards.forEach(cd -> {
                if (cd.getCard().getName().equalsIgnoreCase(cardDeck.getCard().getName())) {
                    totalWithSameName.addAndGet(cd.getQuantity());
                }
            });
        }

        if (totalWithSameName.get() <= 0 || (!isRelentless && totalWithSameName.get() > 4)) {
            throw new IllegalArgumentException("Quantidade inválida. Lembre-se da limitação de 4 cards com mesmo nome");
        }

        return true;
    }

}
