package br.com.bertolucci.mtgtools.deckbuilder.application;

import br.com.bertolucci.mtgtools.deckbuilder.application.card.DownloadCardImageService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;

import java.util.List;

public interface DeckBuilderService {

    List<Set> getSets();

    List<Symbol> getSymbols();

    List<CardDeck> getCardDeckByDeck(Integer deckId);

    List<Card> getCardsBySet(Integer setId);

    Integer getTotalCardsBySet(Integer setId);

    List<Face> getFacesByCard(Integer cardId);

    List<Part> getPartsByCard(Integer cardId);

    List<Ruling> getRulingsByCard(Integer cardId);

    List<Deck> getDecks();

    void saveCard(Card card);

    void saveFace(Face face);

    void savePart(Part part);

    void saveRuling(Ruling ruling);

    void saveDeck(Deck deck);

    void updateCard(Card card);

    void updateFace(Face face);

    void updatePart(Part part);

    void updateRuling(Ruling ruling);

    void updateDeck(Deck deck);

    void removeCard(Card card);

    void removeFace(Face face);

    void removePart(Part part);

    void removeRuling(Ruling ruling);

    void removeDeck(Deck deck);

    void importCardsBySet(String setCode) throws NoApiConnectionException;

    void importRulingsByCard(Card card) throws NoApiConnectionException;

    void importPrintedFieldsByCard(Card card) throws NoApiConnectionException;

    void addCardToDeck(CardDeck cardDeck, boolean isRelentless) throws NoApiConnectionException;

    List<Card> findCards(String name, Integer setId, List<String> keywords, List<String> types,
                         String manaCost, String power, String toughness, String loyalty,
                         Boolean blackCards, Boolean greenCards, Boolean redCards,
                         Boolean blueCards, Boolean whiteCards, Double cmc, Rarity rarity, String format);

    DownloadCardImageService downloadCardImage(Card card);

    void downloadSetImages();

    void downloadSymbolImages();
}
