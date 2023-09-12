package br.com.bertolucci.mtgtools.deckbuilder.application.card;


import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;

import java.util.ArrayList;
import java.util.List;

public class VerifyCardCollectionService {

    private List<CardDto> cardDtos;
    private CollectionService collectionService;
    private Set set;

    public VerifyCardCollectionService(List<CardDto> cardDtos, CollectionService collectionService, Set set) {
        this.cardDtos = cardDtos;
        this.collectionService = collectionService;
        this.set = set;
    }

    public List<CardDto> returnNonDuplicated() {
        List<CardDto> filtered = new ArrayList<>();
        List<Card> collectionCards = collectionService.findCardsBySet(set.getId());

        cardDtos.forEach(cardDto -> {
            Card card = new Card(cardDto);
            card.setSet(set);

            if (!collectionCards.contains(card)) {
                filtered.add(cardDto);
            }
        });

        return filtered;
    }

}
