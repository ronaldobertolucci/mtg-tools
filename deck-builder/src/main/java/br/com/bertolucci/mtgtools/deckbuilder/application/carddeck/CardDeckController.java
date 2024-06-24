package br.com.bertolucci.mtgtools.deckbuilder.application.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.infra.Repository;

public class CardDeckController {

    private Repository<CardDeck> cardDeckController;

    public CardDeckController(Repository<CardDeck> cardDeckController) {
        this.cardDeckController = cardDeckController;
    }

    public void delete(Integer id) {
        cardDeckController.removeById(id);
    }

}
