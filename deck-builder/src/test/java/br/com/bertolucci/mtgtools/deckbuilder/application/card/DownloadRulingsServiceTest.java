package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.SaveService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Ruling;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.RulingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DownloadRulingsServiceTest {

    @Mock
    private DownloadService downloadService;
    @Mock
    private SaveService saveService;
    @Mock
    private CollectionService collectionService;
    private DownloadRulingsService downloadRulingsService;

    @BeforeEach
    void setup() throws NoApiConnectionException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(downloadService.downloadRulings(Mockito.any())).thenReturn(getRulingDtos());
        Mockito.when(collectionService.getSaveService()).thenReturn(saveService);
        downloadRulingsService = new DownloadRulingsService(new Card(), collectionService, downloadService);
    }

    @Test
    void testNoRulingInDatabase() throws NoApiConnectionException {
        Mockito.when(collectionService.findRulingsByCard(Mockito.any())).thenReturn(new ArrayList<>());

        downloadRulingsService.init();

        Mockito.verify(collectionService.getSaveService(), Mockito.times(2)).save(Mockito.any(Ruling.class));
    }

    @Test
    void testRulingsInDatabase() throws NoApiConnectionException {
        Mockito.when(collectionService.findRulingsByCard(Mockito.any())).thenReturn(getRulings());

        downloadRulingsService.init();

        Mockito.verifyNoInteractions(collectionService.getSaveService());
    }

    private List<RulingDto> getRulingDtos() {
        List<RulingDto> rulingDtos = new ArrayList<>();
        rulingDtos.add(new RulingDto("wotc", "2020-01-01", "a comment"));
        rulingDtos.add(new RulingDto("wotc", "2020-01-02", "another comment"));
        return rulingDtos;
    }

    private List<Ruling> getRulings() {
        List<Ruling> rulings = new ArrayList<>();
        List<RulingDto> rulingDtos = getRulingDtos();
        rulingDtos.forEach(rulingDto -> rulings.add(new Ruling(rulingDto)));
        return rulings;
    }

}