package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Ruling;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.RulingDto;

import java.util.List;

public class DownloadRulingsService {

    private Card card;
    private CollectionService collectionService;
    private DownloadService downloadService;

    public DownloadRulingsService(Card card, CollectionService collectionService, DownloadService downloadService) {
        this.card = card;
        this.collectionService = collectionService;
        this.downloadService = downloadService;
    }

    public void init() throws NoApiConnectionException {
        List<Ruling> localRulings = collectionService.findRulingsByCard(card.getId());
        List<RulingDto> apiRulings = downloadService.downloadRulings(card.getRulingsUri());

        apiRulings.forEach(rulingDto -> {
            Ruling ruling = new Ruling(rulingDto);
            ruling.setCard(card);

            if (!localRulings.contains(ruling)) {
                collectionService.getSaveService().save(ruling);
            }
        });
    }
}
