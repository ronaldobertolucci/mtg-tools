package br.com.bertolucci.mtgtools.deckbuilder.application.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeckRepositoryImpl;
import br.com.bertolucci.mtgtools.deckbuilder.infra.Repository;

public class CardDeckController {

    private Repository<CardDeck> cardDeckRepository;

    public CardDeckController(Repository<CardDeck> cardDeckRepository) {
        this.cardDeckRepository = cardDeckRepository;
    }

    public void delete(Integer id) {
        cardDeckRepository.removeById(id);
    }
    
    public void refresh(CardDeck cardDeck) {
        CardDeckRepositoryImpl repository =  (CardDeckRepositoryImpl) cardDeckRepository;
        repository.refresh(cardDeck);
    }

}
