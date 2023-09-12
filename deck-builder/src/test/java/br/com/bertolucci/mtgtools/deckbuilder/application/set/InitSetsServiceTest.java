package br.com.bertolucci.mtgtools.deckbuilder.application.set;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.SaveService;
import br.com.bertolucci.mtgtools.deckbuilder.application.UpdateService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.set.SetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class InitSetsServiceTest {

    @Mock
    private DownloadService downloadService;
    @Mock
    private SaveService saveService;
    @Mock
    private UpdateService updateService;
    @Mock
    private CollectionService collectionService;
    private InitSetsService initSetsService;

    @BeforeEach
    void setup() throws NoApiConnectionException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(downloadService.downloadSets()).thenReturn(getSetDtos());
        Mockito.when(collectionService.getUpdateService()).thenReturn(updateService);
        Mockito.when(collectionService.getSaveService()).thenReturn(saveService);
        initSetsService = new InitSetsService(downloadService, collectionService);
    }

    @Test
    void testNoSetsInDatabase() throws NoApiConnectionException {
        Mockito.when(collectionService.findAllSets()).thenReturn(new ArrayList<>()).thenReturn(getSetsWithoutToken());

        initSetsService.init();

        Mockito.verify(collectionService.getSaveService(), Mockito.times(3)).save(Mockito.any(Set.class));
    }

    @Test
    void testSetsInDatabase() throws NoApiConnectionException {
        Mockito.when(collectionService.findAllSets()).thenReturn(getSetsWithToken());

        initSetsService.init();

        Mockito.verifyNoInteractions(collectionService.getSaveService());
    }

    @Test
    void testUpdateSets() throws NoApiConnectionException {
        Mockito.when(collectionService.findAllSets()).thenReturn(getSetsWithToken());
        Mockito.when(collectionService.findSetByCode(Mockito.any())).thenReturn(getSet("test", "test", "core"));

        initSetsService.init();

        Mockito.verifyNoInteractions(collectionService.getSaveService());
        Mockito.verify(collectionService.getUpdateService(), Mockito.times(3)).update(Mockito.any(Set.class));
    }

    private Set getSet(String name, String code, String type) {
        return new Set(name, code, type, 100, false, "teste.com", null);
    }

    private SetDto getSetDto(String name, String code, String type) {
        return new SetDto(code, name, type, 100, false, "teste.com", null, null, "zen");
    }

    private List<Set> getSetsWithoutToken() {
        List<Set> sets = new ArrayList<>();
        Set set = getSet("Zendikar", "zen", "expansion");

        sets.add(set);
        sets.add(getSet("Core 2019", "m19", "core"));
        return sets;
    }

    private List<Set> getSetsWithToken() {
        List<Set> sets = new ArrayList<>();
        Set set = getSet("Zendikar", "zen", "expansion");

        sets.add(set);
        sets.add(getSet("Core 2019", "m19", "core"));
        sets.add(getSet("Zendikar Tokens", "tzen", "token"));
        return sets;
    }

    private List<SetDto> getSetDtos() {
        List<SetDto> setDtos = new ArrayList<>();
        setDtos.add(getSetDto("Zendikar", "zen", "expansion"));
        setDtos.add(getSetDto("Core 2019", "m19", "core"));
        setDtos.add(getSetDto("Test", "ttt", "funny"));
        setDtos.add(getSetDto("Zendikar Tokens", "tzen", "token"));
        return setDtos;
    }

}