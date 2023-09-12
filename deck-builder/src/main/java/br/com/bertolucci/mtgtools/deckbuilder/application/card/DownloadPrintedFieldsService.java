package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.PrintedFields;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.CardDto;

public class DownloadPrintedFieldsService {

    private Card card;
    private CollectionService collectionService;
    private DownloadService downloadService;

    public DownloadPrintedFieldsService(Card card, CollectionService collectionService,
                                        DownloadService downloadService) {
        this.card = card;
        this.collectionService = collectionService;
        this.downloadService = downloadService;
    }

    public void init() throws NoApiConnectionException {
        String uri = "https://api.scryfall.com/cards/"
                + card.getSet().getCode() + "/"
                + card.getCollectorNumber() + "/"
                + "pt";
        CardDto cardDto = downloadService.downloadCard(uri);

        if (cardDto.collectorNumber() == null) {
            return;
        }

        PrintedFields printedFields;

        if (cardDto.faceDtos() == null) {
            printedFields = new PrintedFields(cardDto.printedName(), cardDto.printedTypeLine(),
                    cardDto.printedText());

            card.setPrintedFields(printedFields);
            collectionService.getUpdateService().update(card);
            return;
        }

        String printedName = cardDto.faceDtos().get(0).printedName()
                + " // "
                + cardDto.faceDtos().get(1).printedName();

        String printedTypeLine = cardDto.faceDtos().get(0).printedTypeLine()
                + " // "
                + cardDto.faceDtos().get(1).printedTypeLine();

        String printedText = cardDto.faceDtos().get(0).printedText()
                + "\n\n//\n\n"
                + cardDto.faceDtos().get(1).printedText();

        printedFields = new PrintedFields(printedName, printedTypeLine, printedText);
        card.setPrintedFields(printedFields);
        collectionService.getUpdateService().update(card);
    }

}
