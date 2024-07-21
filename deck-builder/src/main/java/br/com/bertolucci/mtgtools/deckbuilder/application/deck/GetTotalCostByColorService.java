package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.application.util.GetSymbolList;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.shared.card.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class GetTotalCostByColorService {

    public static Map<Color, Integer> getTotal(Deck deck) {
        Map<Color, Integer> map = new HashMap<>();
        AtomicReference<Integer> redTotal = new AtomicReference<>(0);
        AtomicReference<Integer> blueTotal = new AtomicReference<>(0);
        AtomicReference<Integer> whiteTotal = new AtomicReference<>(0);
        AtomicReference<Integer> blackTotal = new AtomicReference<>(0);
        AtomicReference<Integer> greenTotal = new AtomicReference<>(0);
        AtomicReference<Integer> colorlessTotal = new AtomicReference<>(0);

        deck.getCards().forEach(cd -> {
            List<String> symbols = GetSymbolList.get(cd.getCard().getManaCost());

            for (String symbol: symbols) {
                if (symbol.toLowerCase().contains("R".toLowerCase())) {
                    redTotal.getAndSet(redTotal.get() + cd.getQuantity());
                }

                if (symbol.toLowerCase().contains("U".toLowerCase())) {
                    blueTotal.getAndSet(blueTotal.get() + cd.getQuantity());
                }

                if (symbol.toLowerCase().contains("W".toLowerCase())) {
                    whiteTotal.getAndSet(whiteTotal.get() + cd.getQuantity());
                }

                if (symbol.toLowerCase().contains("B".toLowerCase())) {
                    blackTotal.getAndSet(blackTotal.get() + cd.getQuantity());
                }

                if (symbol.toLowerCase().contains("G".toLowerCase())) {
                    greenTotal.getAndSet(greenTotal.get() + cd.getQuantity());
                }

                if (symbol.toLowerCase().contains("C".toLowerCase())) {
                    colorlessTotal.getAndSet(colorlessTotal.get() + cd.getQuantity());
                }
            }
        });

        map.put(Color.R, redTotal.get());
        map.put(Color.U, blueTotal.get());
        map.put(Color.W, whiteTotal.get());
        map.put(Color.B, blackTotal.get());
        map.put(Color.G, greenTotal.get());
        map.put(Color.C, colorlessTotal.get());

        return map;
    }

}
