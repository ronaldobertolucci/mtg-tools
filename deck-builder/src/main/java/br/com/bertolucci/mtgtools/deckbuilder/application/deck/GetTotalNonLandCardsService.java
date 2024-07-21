package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;

import java.util.concurrent.atomic.AtomicInteger;

public class GetTotalNonLandCardsService {

    public static Integer getTotal(Deck deck) {
        AtomicInteger total = new AtomicInteger();
        deck.getCards().forEach(cd -> {
            if (!cd.getCard().getTypeLine().toLowerCase().contains("Land".toLowerCase())) {
                total.addAndGet(cd.getQuantity());
            }
        });
        return total.get();
    }

}
