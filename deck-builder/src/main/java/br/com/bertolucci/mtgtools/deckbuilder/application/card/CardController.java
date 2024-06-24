package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.infra.Repository;
import br.com.bertolucci.mtgtools.shared.card.CardSearchParametersDto;

import java.util.List;

public class CardController {

    private Repository<Card> cardRepository;

    public CardController(Repository<Card> cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void saveAll(List<Card> cards) {
        cardRepository.saveAll(cards);
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public List<Card> findAllBySet(Integer setId) {
        CardRepositoryImpl cardRepositoryImpl = (CardRepositoryImpl) cardRepository;
        return cardRepositoryImpl.findAllBySet(setId);
    }

    public List<Card> findWithParameter(CardSearchParametersDto cardSearchParametersDto, int limit) {
        CardRepositoryImpl cardRepositoryImpl = (CardRepositoryImpl) cardRepository;
        return cardRepositoryImpl.findWithParameters(cardSearchParametersDto, limit);
    }

    public Integer totalCardsBySet(Integer setId) {
        CardRepositoryImpl cardRepositoryImpl = (CardRepositoryImpl) cardRepository;
        return Math.toIntExact(cardRepositoryImpl.totalCardsBySet(setId));
    }

    public void removeAll(List<Card> cards) {
        cardRepository.removeAll(cards);
    }

    public void updateAll(List<Card> cards) {
        cardRepository.updateAll(cards);
    }

}
