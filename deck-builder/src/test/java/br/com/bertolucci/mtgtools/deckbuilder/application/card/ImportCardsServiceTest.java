package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.SaveService;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.FindSetService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportCardsServiceTest {

    @Mock
    private DownloadService downloadService;
    @Mock
    private CollectionService collectionService;
    @Mock
    private SaveService saveService;
    @Mock
    private FindSetService findSetService;
    private ImportCardsService importCardsService;

    @BeforeEach
    void setup() throws NoApiConnectionException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(downloadService.downloadCardsBySet(Mockito.anyString())).thenReturn(getCardDtos());
        Mockito.when(collectionService.getSaveService()).thenReturn(saveService);
        Mockito.when(collectionService.findCardsBySet(Mockito.any())).thenReturn(new ArrayList<>());
        Mockito.when(collectionService.findSetByCode("ttt")).thenReturn(getSet());
        importCardsService = new ImportCardsService(downloadService, collectionService, findSetService);
    }

    @Test
    void testSave() throws NoApiConnectionException {
        importCardsService.importBySet("ttt");

        Mockito.verify(collectionService.getSaveService(), Mockito.times(3)).save(Mockito.any());
    }

    private List<CardDto> getCardDtos() {
        List<CardDto> cardDtos = new ArrayList<>();
        cardDtos.add(getCardDto("a name", "1"));
        cardDtos.add(getCardDto("a name two", "2"));
        cardDtos.add(getCardDto("a name three", "3"));
        return cardDtos;
    }

    private Set getSet() {
        return new Set("test", "ttt", "core", 3, "test.com", "2020-01-01");
    }

    private CardDto getCardDto(String name, String collectorNumber) {
        return new CardDto("black", collectorNumber, "missing", "common", false,
                null, "en", null, null, null, null, "rony",
                "a flavor", name, "3", "3", "a type", "normal",
                "3", "a oracle", "{3}", null, null, "ttt", 0.0, null, null, null);
    }

}