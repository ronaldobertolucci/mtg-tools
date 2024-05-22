package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.UpdateService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.shared.card.FaceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DownloadPrintedFieldsServiceTest {

    @Mock
    private DownloadService downloadService;
    @Mock
    private UpdateService updateService;
    @Mock
    private CollectionService collectionService;
    @Captor
    private ArgumentCaptor<Card> captor;
    private DownloadPrintedFieldsService downloadPrintedFieldsService;

    @BeforeEach
    void setup() throws NoApiConnectionException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(collectionService.getUpdateService()).thenReturn(updateService);
        downloadPrintedFieldsService = new DownloadPrintedFieldsService(getCard(), collectionService, downloadService);
    }

    @Test
    void testNoResult() throws NoApiConnectionException {
        Mockito.when(downloadService.downloadCard(Mockito.any())).thenReturn(getCardDto(null, null, null, null, null));

        downloadPrintedFieldsService.init();

        Mockito.verifyNoInteractions(collectionService.getUpdateService());
    }

    @Test
    void testCardWithoutFaces() throws NoApiConnectionException {
        Mockito.when(downloadService.downloadCard(Mockito.any())).thenReturn(
                getCardDto("100", null, "Um nome em português", "Um tipo", "Um texto"));

        downloadPrintedFieldsService.init();

        Mockito.verify(collectionService.getUpdateService()).update(captor.capture());

        Card card = captor.getValue();
        assertEquals("Um nome em português", card.getPrintedFields().getPrintedName());
        assertEquals("Um tipo", card.getPrintedFields().getPrintedTypeLine());
        assertEquals("Um texto", card.getPrintedFields().getPrintedText());
    }

    @Test
    void testCardWithFaces() throws NoApiConnectionException {
        Mockito.when(downloadService.downloadCard(Mockito.any())).thenReturn(
                getCardDto("100", getFaces(), "Um nome em português", "Um tipo", "Um texto"));

        downloadPrintedFieldsService.init();

        Mockito.verify(collectionService.getUpdateService()).update(captor.capture());

        Card card = captor.getValue();
        assertEquals("1 // 2", card.getPrintedFields().getPrintedName());
        assertEquals("1 // 2", card.getPrintedFields().getPrintedTypeLine());
        assertEquals("1\n\n//\n\n2", card.getPrintedFields().getPrintedText());
    }

    private CardDto getCardDto(String collectorNumber, List<FaceDto> faces, String printedName, String printedTypeLine,
                               String printedText) {
        return new CardDto("black", collectorNumber, "missing", "common", false,
                null, "en", null, null, faces, null, "rony",
                "a flavor", "a name", "3", "3", "a type", "normal",
                "3", "a oracle", "{3}", null, null, "ttt", 0.0, printedName, printedTypeLine, printedText);
    }

    private Card getCard() {
        Set set = new Set();
        set.setCode("ttt");

        Card card = new Card();
        card.setSet(set);
        return card;
    }

    private List<FaceDto> getFaces() {
        List<FaceDto> faces = new ArrayList<>();
        faces.add(new FaceDto(null, null, null, null, null, null, null, null, null, null, "1", "1", "1"));
        faces.add(new FaceDto(null, null, null, null, null, null, null, null, null, null, "2", "2", "2"));
        return faces;
    }

}