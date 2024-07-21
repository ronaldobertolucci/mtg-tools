package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;

import java.util.concurrent.atomic.AtomicReference;

public class GetTotalDeckCmcService {

    public static Double getTotal(Deck deck) {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        deck.getCards().forEach(cd -> {
            total.updateAndGet(v -> v + cd.getCard().getCmc() * cd.getQuantity().doubleValue());
        });
        return total.get();
    }

}
