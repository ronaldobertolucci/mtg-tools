package br.com.bertolucci.mtgtools.deckbuilder.application;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;

import java.util.List;

public interface CollectionService {

    List<CardDeck> findCardDeckByDeck(Integer deckId);

    List<Card> findCardsBySet(Integer setId);

    List<Card> findCardsWithParameters(String name, Integer setId, List<String> keywords, List<String> types,
                                       String manaCost, String power, String toughness, String loyalty,
                                       Boolean blackCards, Boolean greenCards, Boolean redCards,
                                       Boolean blueCards, Boolean whiteCards, Double cmc, Rarity rarity,
                                       String format);

    Object getTotalCardsBySet(Integer setId);

    List<Face> findFacesByCard(Integer cardId);

    List<Part> findPartsByCard(Integer cardId);

    List<Ruling> findRulingsByCard(Integer cardId);

    List<Deck> findAllDecks();

    List<Set> findAllSets();

    Set findSetByCode(String code);

    List<Symbol> findAllSymbols();

    Symbol findSymbolBySymbol(String symbol);

    RemoveService getRemoveService();

    SaveService getSaveService();

    UpdateService getUpdateService();

    void importCardsBySet(String setCode) throws NoApiConnectionException;
}
