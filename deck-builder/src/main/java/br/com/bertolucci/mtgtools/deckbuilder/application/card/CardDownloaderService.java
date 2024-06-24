package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.ScryfallDownloadService;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import lombok.SneakyThrows;

import java.util.List;

public class CardDownloaderService implements Runnable {

    private final DownloadService downloadService = new ScryfallDownloadService();
    private int start;
    private int end;
    private List<Set> sets;
    private List<Card> cards;

    public CardDownloaderService(int start, int end, List<Set> sets, List<Card> cards) {
        this.start = start;
        this.end = end;
        this.sets = sets;
        this.cards = cards;
    }

    @SneakyThrows({NoApiConnectionException.class, InterruptedException.class})
    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            System.out.println("INFO: Procurando cards na API Scryfall -> Set: " + sets.get(i).getName());
            Thread.sleep(50); //following API Docs
            List<CardDto> downloadedCards = null;

            downloadedCards = downloadService.downloadCardsBySet(sets.get(i).getCode());

            for (CardDto cardDto: downloadedCards) {
                cards.add(new CardBuilder(cardDto, sets.get(i)).build());
            }
        }
    }

}
