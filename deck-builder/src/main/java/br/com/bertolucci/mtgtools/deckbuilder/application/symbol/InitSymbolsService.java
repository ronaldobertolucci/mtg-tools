package br.com.bertolucci.mtgtools.deckbuilder.application.symbol;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;

import java.util.List;

public class InitSymbolsService {

    private String[] ignoredSymbols = new String[]{"{100}", "{1000000}", "{½}", "{∞}"};
    private DownloadService downloadService;
    private CollectionService collectionService;
    private List<SymbolDto> apiSymbols;

    public InitSymbolsService(DownloadService downloadService, CollectionService collectionService) {
        this.downloadService = downloadService;
        this.collectionService = collectionService;
    }

    // verifica se os símbolos da API estão no banco de dados local
    public void init() throws NoApiConnectionException {
        apiSymbols = downloadService.downloadSymbols();

        saveSymbols();
    }

    private void saveSymbols() {
        List<Symbol> localSymbols = collectionService.findAllSymbols();

        apiSymbols.forEach(symbolDto -> {
            Symbol symbol = new Symbol(symbolDto);
            save(localSymbols, symbol);
        });
    }

    private void save(List<Symbol> localSymbols, Symbol symbol) {
        if (!localSymbols.contains(symbol)) {
            for (String s : ignoredSymbols) {
                if (s.equalsIgnoreCase(symbol.getSymbol())) return;
            }

            collectionService.getSaveService().save(symbol);
            return;
        }

        update(symbol.getSymbol());
    }

    private void update(String symbolCode) {
        for (SymbolDto symbolDto : apiSymbols) {
            if (symbolDto.symbol().equalsIgnoreCase(symbolCode)) {
                Symbol symbol = collectionService.findSymbolBySymbol(symbolCode);

                if (symbol == null) {
                    return;
                }

                symbol.setDescription(symbolDto.description());
                symbol.setImageUri(symbolDto.imageUri());
                symbol.setManaValue(symbolDto.manaValue());
                symbol.setRepresentsMana(symbolDto.representsMana());
                collectionService.getUpdateService().update(symbol);
            }
        }
    }

}
