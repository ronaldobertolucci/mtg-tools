package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.DownloadCardImageService;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.DownloadPrintedFieldsService;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.DownloadRulingsService;
import br.com.bertolucci.mtgtools.deckbuilder.application.carddeck.VerifyCardDeckLegalitiesService;
import br.com.bertolucci.mtgtools.deckbuilder.application.deck.AddCardToDeckService;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.DownloadSetImagesService;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.InitSetsService;
import br.com.bertolucci.mtgtools.deckbuilder.application.symbol.DownloadSymbolImagesService;
import br.com.bertolucci.mtgtools.deckbuilder.application.symbol.InitSymbolsService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.downloader.ScryfallDownloadService;
import br.com.bertolucci.mtgtools.pngsvgtools.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.DownloadImageServiceImpl;

import java.util.Comparator;
import java.util.List;

public class DeckBuilderServiceImpl implements DeckBuilderService {

    private final DownloadService downloadService = new ScryfallDownloadService();
    private final CollectionService collectionService = new CollectionServiceImpl(downloadService);
    private final DownloadImageService downloadImageService = new DownloadImageServiceImpl();
    private List<Set> sets;
    private List<Symbol> symbols;

    public DeckBuilderServiceImpl() throws NoApiConnectionException {
        new InitSetsService(downloadService, collectionService).init();
        new InitSymbolsService(downloadService, collectionService).init();

        check();
    }

    private void check() {
        sets = collectionService.findAllSets();
        sets.sort(Comparator.comparing(Set::getName));

        symbols = collectionService.findAllSymbols();
        symbols.sort(Comparator.comparing(Symbol::getSymbol));

        if (sets.isEmpty()) {
            throw new RuntimeException("Não há sets cadastrados. O programa não pode ser executado");
        }
    }

    @Override
    public List<Set> getSets() {
        return sets;
    }

    @Override
    public List<Symbol> getSymbols() {
        return symbols;
    }

    @Override
    public List<CardDeck> getCardDeckByDeck(Integer deckId) {
        return collectionService.findCardDeckByDeck(deckId);
    }

    @Override
    public List<Card> getCardsBySet(Integer setId) {
        return collectionService.findCardsBySet(setId);
    }

    @Override
    public Integer getTotalCardsBySet(Integer setId) {
        return (Integer) collectionService.getTotalCardsBySet(setId);
    }

    @Override
    public List<Face> getFacesByCard(Integer cardId) {
        return collectionService.findFacesByCard(cardId);
    }

    @Override
    public List<Part> getPartsByCard(Integer cardId) {
        return collectionService.findPartsByCard(cardId);
    }

    @Override
    public List<Ruling> getRulingsByCard(Integer cardId) {
        return collectionService.findRulingsByCard(cardId);
    }

    @Override
    public List<Deck> getDecks() {
        return collectionService.findAllDecks();
    }

    @Override
    public void saveCard(Card card) {
        collectionService.getSaveService().save(card);
    }

    @Override
    public void saveFace(Face face) {
        collectionService.getSaveService().save(face);
    }

    @Override
    public void savePart(Part part) {
        collectionService.getSaveService().save(part);
    }

    @Override
    public void saveRuling(Ruling ruling) {
        collectionService.getSaveService().save(ruling);
    }

    @Override
    public void saveDeck(Deck deck) {
        collectionService.getSaveService().save(deck);
    }

    @Override
    public void updateCard(Card card) {
        collectionService.getUpdateService().update(card);
    }

    @Override
    public void updateFace(Face face) {
        collectionService.getUpdateService().update(face);
    }

    @Override
    public void updatePart(Part part) {
        collectionService.getUpdateService().update(part);
    }

    @Override
    public void updateRuling(Ruling ruling) {
        collectionService.getUpdateService().update(ruling);
    }

    @Override
    public void updateDeck(Deck deck) {
        collectionService.getUpdateService().update(deck);
        new VerifyCardDeckLegalitiesService(collectionService, deck).run();
    }

    @Override
    public void updateCardDeck(CardDeck cardDeck) {
        collectionService.getUpdateService().update(cardDeck);
    }

    @Override
    public void removeCard(Card card) {
        collectionService.getRemoveService().remove(card);
    }

    @Override
    public void removeFace(Face face) {
        collectionService.getRemoveService().remove(face);
    }

    @Override
    public void removePart(Part part) {
        collectionService.getRemoveService().remove(part);
    }

    @Override
    public void removeRuling(Ruling ruling) {
        collectionService.getRemoveService().remove(ruling);
    }

    @Override
    public void removeDeck(Deck deck) {
        collectionService.getRemoveService().remove(deck);
    }

    @Override
    public void removeCardDeck(CardDeck cardDeck) {
        collectionService.getRemoveService().remove(cardDeck);
    }

    @Override
    public void importCardsBySet(String setCode) throws NoApiConnectionException {
        collectionService.importCardsBySet(setCode);
    }

    @Override
    public void importRulingsByCard(Card card) throws NoApiConnectionException {
        new DownloadRulingsService(card, collectionService, downloadService).init();
    }

    @Override
    public void importPrintedFieldsByCard(Card card) throws NoApiConnectionException {
        new DownloadPrintedFieldsService(card, collectionService, downloadService).init();
    }

    @Override
    public void addCardToDeck(CardDeck cardDeck, boolean isRelentless) throws NoApiConnectionException {
        if (new AddCardToDeckService(collectionService, cardDeck, isRelentless).add()) {
            new DownloadCardImageService(cardDeck.getCard(), collectionService, downloadImageService).init();
            new DownloadRulingsService(cardDeck.getCard(), collectionService, downloadService).init();
            new DownloadPrintedFieldsService(cardDeck.getCard(), collectionService, downloadService).init();
        }
    }

    @Override
    public List<Card> findCards(String name, Integer setId, List<String> keywords, List<String> types, String manaCost,
                                String power, String toughness, String loyalty, Boolean blackCards, Boolean greenCards,
                                Boolean redCards, Boolean blueCards, Boolean whiteCards, Double cmc, Rarity rarity,
                                String format) {

        return collectionService.findCardsWithParameters(name, setId, keywords, types, manaCost, power, toughness,
                loyalty, blackCards, greenCards, redCards, blueCards, whiteCards, cmc, rarity, format);
    }

    @Override
    public DownloadCardImageService downloadCardImage(Card card) {
        DownloadCardImageService dcis = new DownloadCardImageService(card, collectionService, downloadImageService);
        dcis.init();
        return dcis;
    }

    @Override
    public void downloadSetImages() {
        new DownloadSetImagesService(downloadImageService).init(getSets());
    }

    @Override
    public void downloadSymbolImages() {
        new DownloadSymbolImagesService(downloadImageService).init(getSymbols());
    }
}
