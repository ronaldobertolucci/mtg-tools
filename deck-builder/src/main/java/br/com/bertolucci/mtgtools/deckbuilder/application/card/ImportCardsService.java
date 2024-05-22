package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.FindSetService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.CardDto;

import java.util.ArrayList;
import java.util.List;

public class ImportCardsService {

    private DownloadService downloadService;
    private CollectionService collectionService;
    private FindSetService findSetService;

    public ImportCardsService(DownloadService downloadService, CollectionService collectionService,
                              FindSetService findSetService) {
        this.downloadService = downloadService;
        this.collectionService = collectionService;
        this.findSetService = findSetService;
    }

    private List<CardDto> download(Set set) throws NoApiConnectionException {
        List<CardDto> cardDtos = downloadService.downloadCardsBySet(set.getCode());
        return new VerifyCardCollectionService(cardDtos, collectionService, set).returnNonDuplicated();
    }

    public void importBySet(String setCode) throws NoApiConnectionException {
        Set set = collectionService.findSetByCode(setCode);
        List<CardDto> cardDtos = download(set);
        List<Card> cards = new ArrayList<>();
        cardDtos.forEach(cardDto -> {
            Card c = new CardFromDtoService(collectionService, findSetService, cardDto, set).get();
            cards.add(c);
        });

        saveAll(cards);
    }

    private void saveAll(List<Card> list) {
        collectionService.getSaveService().saveAll(list);
    }
}
