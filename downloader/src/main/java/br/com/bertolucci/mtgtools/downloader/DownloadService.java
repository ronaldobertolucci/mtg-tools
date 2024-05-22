package br.com.bertolucci.mtgtools.downloader;

import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.shared.card.RulingDto;
import br.com.bertolucci.mtgtools.shared.set.SetDto;
import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;

import java.util.List;

public interface DownloadService {
    List<CardDto> downloadCardsBySet(String setCode) throws NoApiConnectionException;

    List<SetDto> downloadSets() throws NoApiConnectionException;

    List<SymbolDto> downloadSymbols() throws NoApiConnectionException;

    List<RulingDto> downloadRulings(String uri) throws NoApiConnectionException;

    CardDto downloadCard(String uri) throws NoApiConnectionException;

    SetDto downloadSet(String uri) throws NoApiConnectionException;

    String getCardsUri(String uri) throws NoApiConnectionException;
}
