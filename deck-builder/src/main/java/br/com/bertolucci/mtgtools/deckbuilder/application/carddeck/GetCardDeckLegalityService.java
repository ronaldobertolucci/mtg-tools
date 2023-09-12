package br.com.bertolucci.mtgtools.deckbuilder.application.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;

import java.lang.reflect.Field;

public class GetCardDeckLegalityService {

    public static Object get(Card card, Deck deck) throws NoSuchFieldException, IllegalAccessException {
        Field legality = card.getLegalities().getClass().getDeclaredField(deck.getFormat().name().toLowerCase());
        legality.setAccessible(true);
        return legality.get(card.getLegalities());
    }

}
