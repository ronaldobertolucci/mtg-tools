package br.com.bertolucci.mtgtools.downloader;

import br.com.bertolucci.mtgtools.downloader.adapter.ApiAdapter;
import br.com.bertolucci.mtgtools.downloader.adapter.ScryfallAdapter;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.downloader.util.HttpConnectionUtil;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.shared.card.RulingDto;
import br.com.bertolucci.mtgtools.shared.set.SetDto;
import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;

import java.util.List;

public class ScryfallDownloadService implements DownloadService {

    private ApiAdapter apiAdapter;

    public ScryfallDownloadService() {
        this.apiAdapter = new ScryfallAdapter(new HttpConnectionUtil());
    }

    @Override
    public List<CardDto> downloadCardsBySet(String setCode) throws NoApiConnectionException {
        String uri = getCardsUri("https://api.scryfall.com/sets/" + setCode);
        return apiAdapter.getList(uri, CardDto.class);
    }

    @Override
    public List<SetDto> downloadSets() throws NoApiConnectionException {
        return apiAdapter.getList("https://api.scryfall.com/sets/", SetDto.class);
    }

    @Override
    public List<SymbolDto> downloadSymbols() throws NoApiConnectionException {
        return apiAdapter.getList("https://api.scryfall.com/symbology/", SymbolDto.class);
    }

    @Override
    public List<RulingDto> downloadRulings(String uri) throws NoApiConnectionException {
        return apiAdapter.getList(uri, RulingDto.class);
    }

    @Override
    public CardDto downloadCard(String uri) throws NoApiConnectionException {
        return apiAdapter.getObject(uri, CardDto.class);
    }

    @Override
    public SetDto downloadSet(String uri) throws NoApiConnectionException {
        return apiAdapter.getObject(uri, SetDto.class);
    }

    @Override
    public String getCardsUri(String uri) throws NoApiConnectionException {
        SetDto setDto = apiAdapter.getObject(uri, SetDto.class);
        return setDto.cardsUri();
    }
}
