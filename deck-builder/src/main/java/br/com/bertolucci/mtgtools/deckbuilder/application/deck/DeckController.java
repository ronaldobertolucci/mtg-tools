package br.com.bertolucci.mtgtools.deckbuilder.application.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.*;
import br.com.bertolucci.mtgtools.deckbuilder.infra.Repository;

import java.util.List;

public class DeckController {

    private Repository<Deck> deckRepository;

    public DeckController(Repository<Deck> deckRepository) {
        this.deckRepository = deckRepository;
    }

    public void save(Deck deck) {
        deckRepository.save(deck);
    }

    public List<Deck> findAll() {
        return deckRepository.findAll();
    }

    public void delete(Integer id) {
        deckRepository.removeById(id);
    }

    public void update(Deck deck) {
        deckRepository.update(deck);
    }

}
