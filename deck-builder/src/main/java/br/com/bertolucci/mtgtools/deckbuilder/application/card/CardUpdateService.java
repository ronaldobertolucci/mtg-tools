package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.update.LastUpdateController;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardLegality;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.deckbuilder.domain.update.LastUpdate;
import br.com.bertolucci.mtgtools.deckbuilder.infra.util.MultiThreadUtil;
import br.com.bertolucci.mtgtools.deckbuilder.infra.util.MultiThreadWaitLogUtil;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;

import java.util.ArrayList;
import java.util.List;

public class CardUpdateService {

    private List<Card> downloadedCards = new ArrayList<>();
    private List<Card> cardsToBeMerged = new ArrayList<>();
    private CardController cardController;
    private LastUpdateController updateController;
    private List<Set> sets;

    public CardUpdateService(List<Set> sets, CardController cardController, LastUpdateController updateController) {
        this.sets = sets;
        this.cardController = cardController;
        this.updateController = updateController;
    }

    public void update() throws NoApiConnectionException, InterruptedException {
        downloadCards(sets);

        for (Set set: sets) {
            System.out.println("INFO: Comparando legalidades e status de imagem do Set " + set.getName());
            List<Card> dbCards = cardController.findAllBySet(set.getId());

            for (Card card: dbCards) {
                boolean updateFlag = false;
                Card downloadedCard = downloadedCards.stream().filter(c -> c.equals(card)).findFirst().orElse(null);

                if (downloadedCard != null) {
                    if (card.getImageStatus().compareTo(downloadedCard.getImageStatus()) != 0) {
                        card.setImageStatus(downloadedCard.getImageStatus());
                        card.setImageUri(downloadedCard.getImageUri());

                        for (Face face: card.getFaces()) {
                            downloadedCard.getFaces()
                                    .stream()
                                    .filter(f -> f.equals(face))
                                    .findFirst()
                                    .ifPresent(downloadedFace -> face.setImageUri(downloadedFace.getImageUri()));
                        }
                    }

                    for (CardLegality cardLegality: card.getLegalities()) {
                        CardLegality downloadedCardLegality = downloadedCard.getLegalities()
                                .stream()
                                .filter(cl -> cl.getDeckFormat().equals(cardLegality.getDeckFormat()))
                                .findFirst()
                                .orElse(null);

                        if (downloadedCardLegality != null) {
                            if (downloadedCardLegality.getLegality() != cardLegality.getLegality()) {
                                cardLegality.setLegality(downloadedCardLegality.getLegality());
                                updateFlag = true;
                            }
                        }
                    }
                }

                if (updateFlag) cardsToBeMerged.add(card);
            }
        }

        updateCards();
    }

    private void downloadCards(List<Set> sets) throws NoApiConnectionException, InterruptedException {
        MultiThreadUtil multiThreadUtil = new MultiThreadUtil(12, sets) {
            @Override
            protected Runnable setRunnable(int start, int end) {
                return new CardDownloaderService(start, end, sets, downloadedCards);
            }
        };
        multiThreadUtil.start();
    }

    private void updateCards() throws InterruptedException {
        MultiThreadWaitLogUtil multiThreadWaitLogUtil = new MultiThreadWaitLogUtil() {
            @Override
            protected Runnable setRunnable() {
                return () -> cardController.updateAll(cardsToBeMerged);
            }
        };
        multiThreadWaitLogUtil.start("INFO: Atualizando cards no banco de dados");
        updateController.save(new LastUpdate());
    }
}
