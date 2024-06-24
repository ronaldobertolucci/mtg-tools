package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardLegality;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legality;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;

import java.util.List;

public class CheckLegalitiesService {

    private Deck deck;
    private DeckBuilderService deckBuilderService;

    public CheckLegalitiesService(Deck deck, DeckBuilderService deckBuilderService) {
        this.deck = deck;
        this.deckBuilderService = deckBuilderService;
    }

    public boolean hasIllegal() {
        List<Card> cards = deck.getCards().stream().map(CardDeck::getCard).toList();

        for (Card card: cards) {
            Legality legality = getLegality(card, deck.getDeckFormat());

            if (!isLegal(legality)) return true;
        }

        return false;
    }

    private static boolean isLegal(Legality legality) {
        return legality == Legality.LEGAL || legality == Legality.RESTRICTED;
    }

    public void removeIllegalCards() {
        List<Card> cards = deck.getCards().stream().map(CardDeck::getCard).toList();

        for (Card card: cards) {
            Legality legality = getLegality(card, deck.getDeckFormat());

            if (!isLegal(legality)) {
                CardDeck cardDeck = deck.getCards().stream().filter(cd -> cd.getCard().equals(card))
                        .findFirst()
                        .orElseThrow();
                deckBuilderService.removeCardFromDeck(cardDeck);
            }
        }
    }

    private Legality getLegality(Card card, DeckFormat deckFormat) {
        CardLegality cardLegality = card.getLegalities().stream().filter(cl -> cl.getDeckFormat().equals(deckFormat))
                .findFirst()
                .orElse(null);

        return cardLegality != null ? cardLegality.getLegality() : null;
    }
}
