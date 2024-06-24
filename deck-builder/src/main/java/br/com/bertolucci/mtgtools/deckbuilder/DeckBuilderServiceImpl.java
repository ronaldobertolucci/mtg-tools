package br.com.bertolucci.mtgtools.deckbuilder;

import br.com.bertolucci.mtgtools.deckbuilder.application.card.CardController;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.CardDownloaderService;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.CardImageDownloaderService;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.CardUpdateService;
import br.com.bertolucci.mtgtools.deckbuilder.application.carddeck.CardDeckController;
import br.com.bertolucci.mtgtools.deckbuilder.application.update.LastUpdateController;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeckRepositoryImpl;
import br.com.bertolucci.mtgtools.deckbuilder.domain.update.LastUpdateRepositoryImpl;
import br.com.bertolucci.mtgtools.deckbuilder.infra.util.MultiThreadUtil;
import br.com.bertolucci.mtgtools.deckbuilder.infra.util.MultiThreadWaitLogUtil;
import br.com.bertolucci.mtgtools.shared.card.CardSearchParametersDto;
import br.com.bertolucci.mtgtools.deckbuilder.application.deck.AddCardToDeckService;
import br.com.bertolucci.mtgtools.deckbuilder.application.deck.DeckController;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.SetController;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.SetImageDownloaderService;
import br.com.bertolucci.mtgtools.deckbuilder.application.symbol.SymbolController;
import br.com.bertolucci.mtgtools.deckbuilder.application.symbol.SymbolImageDownloaderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckRepositoryImpl;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.SetBuilder;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.SetRepositoryImpl;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.*;
import br.com.bertolucci.mtgtools.deckbuilder.infra.util.JPAUtil;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.downloader.ScryfallDownloadService;
import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;
import br.com.bertolucci.mtgtools.shared.set.SetDto;
import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.*;

public class DeckBuilderServiceImpl implements DeckBuilderService {

    private final EntityManager em = JPAUtil.getEntityManager();
    private final DownloadService downloadService = new ScryfallDownloadService();
    private final SetController setController = new SetController(new SetRepositoryImpl(em));
    private final SymbolController symbolController = new SymbolController(new SymbolRepositoryImpl(em));
    private final CardController cardController = new CardController(new CardRepositoryImpl(em));
    private final DeckController deckController = new DeckController(new DeckRepositoryImpl(em));
    private final CardDeckController cardDeckController = new CardDeckController(new CardDeckRepositoryImpl(em));
    private final LastUpdateController updateController = new LastUpdateController(new LastUpdateRepositoryImpl(em));
    private final List<Card> cardsToBePersisted = new ArrayList<>();
    private final List<Set> sets = new ArrayList<>();
    private final List<Symbol> symbols = new ArrayList<>();

    public DeckBuilderServiceImpl() throws NoApiConnectionException, InterruptedException, ImageDownloadException {
        prepareSets();
        prepareSymbols();

        while (!checkCards(sets).isEmpty()) {
            downloadNewCards(checkCards(sets));
        }

        this.sets.sort(Comparator.comparing(Set::getName));
    }

    private void prepareSymbols() throws NoApiConnectionException {
        System.out.println("INFO: Criando símbolos");
        symbolController.saveAll(downloadNewSymbols());
        symbols.addAll(symbolController.findAll());
        downloadSymbolImages(symbols);
    }

    private void prepareSets() throws NoApiConnectionException {
        System.out.println("INFO: Criando sets");
        setController.saveAll(downloadNewSets());
        sets.addAll(setController.findCoreSets());
        sets.addAll(setController.findTokenSets());
        Collections.shuffle(sets); //shuffle token sets to improve performance
        downloadSetImages(sets);
    }

    private void downloadSymbolImages(List<Symbol> symbols) {
        MultiThreadUtil multiThreadUtil = new MultiThreadUtil(12, symbols) {
            @Override
            protected Runnable setRunnable(int start, int end) {
                return new SymbolImageDownloaderService(start, end, symbols);
            }
        };
        multiThreadUtil.start();
    }

    private void downloadSetImages(List<Set> sets) {
        MultiThreadUtil multiThreadUtil = new MultiThreadUtil(12, sets) {
            @Override
            protected Runnable setRunnable(int start, int end) {
                return new SetImageDownloaderService(start, end, sets);
            }
        };
        multiThreadUtil.start();
    }

    private List<Set> checkCards(List<Set> sets) {
        List<Set> setsToBeDownloaded = new ArrayList<>();

        sets.forEach(set -> {
            if (set.getTotalCards() > cardController.totalCardsBySet(set.getId())) {
                List<Card> dbCards = cardController.findAllBySet(set.getId());
                cardController.removeAll(dbCards);
                setsToBeDownloaded.add(set);
            }
        });

        return setsToBeDownloaded;
    }

    private void downloadNewCards(List<Set> sets) throws NoApiConnectionException, InterruptedException {
        MultiThreadUtil multiThreadUtil = new MultiThreadUtil(12, sets) {
            @Override
            protected Runnable setRunnable(int start, int end) {
                return new CardDownloaderService(start, end, sets, cardsToBePersisted);
            }
        };
        multiThreadUtil.start();
        saveCards();
    }

    private void saveCards() throws InterruptedException {
        MultiThreadWaitLogUtil multiThreadWaitLogUtil = new MultiThreadWaitLogUtil() {
            @Override
            protected Runnable setRunnable() {
                return () -> cardController.saveAll(cardsToBePersisted);
            }
        };
        multiThreadWaitLogUtil.start("INFO: Persistindo cards no banco de dados");
        cardsToBePersisted.clear();
    }

    private List<Set> downloadNewSets() throws NoApiConnectionException {
        List<SetDto> downloadedSets = downloadService.downloadSets();
        return downloadedSets.stream().map(setDownloadDto -> new SetBuilder(setDownloadDto).build()).toList();
    }

    private List<Symbol> downloadNewSymbols() throws NoApiConnectionException {
        List<SymbolDto> downloadedSymbols = downloadService.downloadSymbols();
        return downloadedSymbols.stream().map(symbolDto -> new SymbolBuilder(symbolDto).build()).toList();
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
    public void saveDeck(Deck deck) {
        deckController.save(deck);
    }

    @Override
    public List<Deck> getDecks() {
        return deckController.findAll();
    }

    @Override
    public void updateDeck(Deck deck) {
        deckController.update(deck);
    }

    @Override
    public void removeDeck(Deck deck) {
        deckController.delete(deck.getId());
    }

    @Override
    public void addCardToDeck(CardDeck cardDeck, boolean isRelentless) {
        new AddCardToDeckService(cardDeck, isRelentless).add();
    }

    @Override
    public void removeCardFromDeck(CardDeck cardDeck) {
        Deck deck = cardDeck.getDeck();
        deck.getCards().remove(cardDeck);
        cardDeckController.delete(cardDeck.getId());
    }

    @Override
    public void downloadCardImage(Card card) {
        new CardImageDownloaderService(card).init();
    }

    @Override
    public List<Card> findCardsByParameter(CardSearchParametersDto cardSearchParametersDto, int limit) {
        return cardController.findWithParameter(cardSearchParametersDto, limit);
    }

    @Override
    public List<Card> findCardsBySet(Integer setId) {
        return cardController.findAllBySet(setId);
    }

    @Override
    public void updateLegalities() throws NoApiConnectionException, InterruptedException {
        new CardUpdateService(sets, cardController, updateController).update();
    }

    @Override
    public LocalDate lastUpdate() {
        return updateController.findLastUpdate();
    }
}
