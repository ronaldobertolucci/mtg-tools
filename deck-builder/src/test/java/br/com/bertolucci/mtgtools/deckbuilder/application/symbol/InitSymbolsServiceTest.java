package br.com.bertolucci.mtgtools.deckbuilder.application.symbol;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.SaveService;
import br.com.bertolucci.mtgtools.deckbuilder.application.UpdateService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class InitSymbolsServiceTest {

    @Mock
    private DownloadService downloadService;
    @Mock
    private SaveService saveService;
    @Mock
    private UpdateService updateService;
    @Mock
    private CollectionService collectionService;
    private InitSymbolsService initSymbolsService;

    @BeforeEach
    void setup() throws NoApiConnectionException {
        MockitoAnnotations.initMocks(this);
        Mockito.when(downloadService.downloadSymbols()).thenReturn(getSymbolDtos());
        Mockito.when(collectionService.getSaveService()).thenReturn(saveService);
        Mockito.when(collectionService.getUpdateService()).thenReturn(updateService);
        initSymbolsService = new InitSymbolsService(downloadService, collectionService);
    }

    @Test
    void testNoSymbolInDatabase() throws NoApiConnectionException {
        Mockito.when(collectionService.findAllSymbols()).thenReturn(new ArrayList<>());

        initSymbolsService.init();

        Mockito.verify(collectionService.getSaveService(), Mockito.times(2)).save(Mockito.any(Symbol.class));
    }

    @Test
    void testSymbolsInDatabase() throws NoApiConnectionException {
        Mockito.when(collectionService.findAllSymbols()).thenReturn(getSymbols());

        initSymbolsService.init();

        Mockito.verifyNoInteractions(collectionService.getSaveService());
    }

    @Test
    void testUpdateSymbols() throws NoApiConnectionException {
        Mockito.when(collectionService.findAllSymbols()).thenReturn(getSymbols());
        Mockito.when(collectionService.findSymbolBySymbol(Mockito.any())).thenReturn(getSymbol("{t}", "test"));

        initSymbolsService.init();

        Mockito.verifyNoInteractions(collectionService.getSaveService());
        Mockito.verify(collectionService.getUpdateService(), Mockito.times(2)).update(Mockito.any(Symbol.class));
    }

    private SymbolDto getSymbolDto(String symbol, String description) {
        return new SymbolDto(symbol, description, true, 1.0,  "test.com");
    }
    private List<SymbolDto> getSymbolDtos() {
        List<SymbolDto> symbolDtos = new ArrayList<>();
        symbolDtos.add(getSymbolDto("{U}", "one blue mana"));
        symbolDtos.add(getSymbolDto("{R}", "one red mana"));
        return symbolDtos;
    }

    private Symbol getSymbol(String symbol, String description) {
        return new Symbol(symbol, description, true, 1.0,  "test.com");
    }
    private List<Symbol> getSymbols() {
        List<Symbol> symbols = new ArrayList<>();
        symbols.add(getSymbol("{U}", "one blue mana"));
        symbols.add(getSymbol("{R}", "one red mana"));
        return symbols;
    }

}