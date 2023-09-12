package br.com.bertolucci.mtgtools.deckbuilder.infra;

import br.com.bertolucci.mtgtools.deckbuilder.application.*;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.ImportCardsService;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.FindSetService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.deckbuilder.infra.util.JPAUtil;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.JoinType;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class CollectionServiceImpl implements CollectionService {

    private final EntityManagerFactory emf = JPAUtil.getFactory();
    private final RemoveService removeService = new RemoveServiceImpl(emf);
    private final UpdateService updateService = new UpdateServiceImpl(emf);
    private final SaveService saveService = new SaveServiceImpl(emf);
    private final FindSetService findSetService = new FindSetService(this);
    private final ImportCardsService importCardsService;

    public CollectionServiceImpl(DownloadService downloadService) {
        this.importCardsService = new ImportCardsService(downloadService, this, findSetService);
    }

    @Override
    public List<CardDeck> findCardDeckByDeck(Integer deckId) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<CardDeck> queryCardDeckService = new QueryServiceImpl<>(em, CardDeck.class);
            queryCardDeckService.getRoot().fetch("card", JoinType.INNER);
            queryCardDeckService.getRoot().fetch("deck", JoinType.INNER);
            queryCardDeckService.addEqual(queryCardDeckService.getRoot().join("deck", JoinType.INNER).get("id"), deckId);
            return queryCardDeckService.getResultList();
        }
    }

    @Override
    public List<Card> findCardsBySet(Integer setId) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Card> queryCardService = new QueryServiceImpl<>(em, Card.class);
            queryCardService.getRoot().fetch("set", JoinType.INNER);
            queryCardService.addEqual(queryCardService.getRoot().join("set", JoinType.INNER).get("id"), setId);
            return queryCardService.getResultList();
        }
    }

    @Override
    public List<Card> findCardsWithParameters(String name, Integer setId, List<String> keywords, List<String> types,
                                              String manaCost, String power, String toughness, String loyalty,
                                              Boolean blackCards, Boolean greenCards, Boolean redCards,
                                              Boolean blueCards, Boolean whiteCards, Double cmc, Rarity rarity,
                                              String format) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Card> queryCardService = new QueryServiceImpl<>(em, Card.class);
            queryCardService.addEqual(queryCardService.getRoot().join("set", JoinType.INNER).get("id"), setId);
            HibernateCriteriaBuilder hcb = (HibernateCriteriaBuilder) queryCardService.getCb();

            if (name != null && !name.trim().isEmpty()) {
                queryCardService.and(hcb.ilike(queryCardService.getRoot().get("name"), "%" + name.trim() + "%", '#'));
            }

            if (setId != null) {
                queryCardService.and(hcb.equal(queryCardService.getRoot().join("set", JoinType.INNER).get("id"), setId));
            }

            if (keywords != null && !keywords.isEmpty()) {
                for (String k : keywords) {
                    queryCardService.and(hcb.ilike(queryCardService.getRoot().get("oracleText"), "%" + k.trim() + "%", '#'));
                }
            }

            if (types != null && !types.isEmpty()) {
                for (String t : types) {
                    queryCardService.and(hcb.ilike(queryCardService.getRoot().get("typeLine"), "%" + t.trim() + "%", '#'));
                }
            }

            if (manaCost != null && !manaCost.trim().isEmpty()) {
                queryCardService.and(hcb.equal(queryCardService.getRoot().get("manaCost"), manaCost.trim()));
            }

            if (power != null && !power.trim().isEmpty()) {
                queryCardService.and(hcb.equal(queryCardService.getRoot().get("power"), power.trim()));
            }

            if (toughness != null && !toughness.trim().isEmpty()) {
                queryCardService.and(hcb.equal(queryCardService.getRoot().get("toughness"), toughness.trim()));
            }

            if (loyalty != null && !loyalty.trim().isEmpty()) {
                queryCardService.and(hcb.equal(queryCardService.getRoot().get("loyalty"), loyalty.trim()));
            }

            if (blackCards != null && blackCards) {
                queryCardService.and(hcb.ilike(queryCardService.getRoot().get("manaCost"), "%{B}%", '#'));
            }

            if (greenCards != null && greenCards) {
                queryCardService.and(hcb.ilike(queryCardService.getRoot().get("manaCost"), "%{G}%", '#'));
            }

            if (redCards != null && redCards) {
                queryCardService.and(hcb.ilike(queryCardService.getRoot().get("manaCost"), "%{R}%", '#'));
            }

            if (blueCards != null && blueCards) {
                queryCardService.and(hcb.ilike(queryCardService.getRoot().get("manaCost"), "%{U}%", '#'));
            }

            if (whiteCards != null && whiteCards) {
                queryCardService.and(hcb.ilike(queryCardService.getRoot().get("manaCost"), "%{W}%", '#'));
            }

            if (cmc != null) {
                queryCardService.and(hcb.equal(queryCardService.getRoot().get("cmc"), cmc));
            }

            if (rarity != null) {
                queryCardService.and(hcb.equal(queryCardService.getRoot().get("rarity"), rarity.ordinal()));
            }

            if (format != null && !format.isEmpty()) {
                queryCardService.and(hcb.or(
                        hcb.equal(queryCardService.getRoot().get("legalities").get(format), Legality.LEGAL.ordinal()),
                        hcb.equal(queryCardService.getRoot().get("legalities").get(format), Legality.RESTRICTED.ordinal()))
                );
            }

            return queryCardService.getResultList();
        }
    }

    @Override
    public Object getTotalCardsBySet(Integer setId) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Card> queryCardService = new QueryServiceImpl<>(em, Card.class);
            queryCardService.addEqual(queryCardService.getRoot().join("set", JoinType.INNER).get("id"), setId);
            return queryCardService.getResultList().size();
        }
    }

    @Override
    public List<Face> findFacesByCard(Integer cardId) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Face> queryFaceService = new QueryServiceImpl<>(em, Face.class);
            queryFaceService.addEqual(queryFaceService.getRoot().join("card", JoinType.INNER).get("id"), cardId);
            return queryFaceService.getResultList();
        }
    }

    @Override
    public List<Part> findPartsByCard(Integer cardId) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Part> queryPartService = new QueryServiceImpl<>(em, Part.class);
            queryPartService.addEqual(queryPartService.getRoot().join("card", JoinType.INNER).get("id"), cardId);
            return queryPartService.getResultList();
        }
    }

    @Override
    public List<Ruling> findRulingsByCard(Integer cardId) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Ruling> queryRulingService = new QueryServiceImpl<>(em, Ruling.class);
            queryRulingService.addEqual(queryRulingService.getRoot().join("card", JoinType.INNER).get("id"), cardId);
            return queryRulingService.getResultList();
        }
    }

    @Override
    public List<Deck> findAllDecks() {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Deck> queryDeckService = new QueryServiceImpl<>(em, Deck.class);
            return queryDeckService.getResultList();
        }
    }

    @Override
    public List<Set> findAllSets() {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Set> querySetService = new QueryServiceImpl<>(em, Set.class);
            return querySetService.getResultList();
        }
    }

    @Override
    public Set findSetByCode(String code) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Set> querySetService = new QueryServiceImpl<>(em, Set.class);
            querySetService.addEqual(querySetService.getRoot().get("code"), code);
            return querySetService.getSingleResult();
        }
    }

    @Override
    public List<Symbol> findAllSymbols() {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Symbol> querySymbolService = new QueryServiceImpl<>(em, Symbol.class);
            return querySymbolService.getResultList();
        }
    }

    @Override
    public Symbol findSymbolBySymbol(String symbol) {
        try (Session em = (Session) this.emf.createEntityManager()) {
            QueryService<Symbol> querySymbolService = new QueryServiceImpl<>(em, Symbol.class);
            querySymbolService.addEqual(querySymbolService.getRoot().get("symbol"), symbol);
            return querySymbolService.getSingleResult();
        }
    }

    @Override
    public RemoveService getRemoveService() {
        return removeService;
    }

    @Override
    public SaveService getSaveService() {
        return saveService;
    }

    @Override
    public UpdateService getUpdateService() {
        return updateService;
    }

    @Override
    public void importCardsBySet(String setCode) throws NoApiConnectionException {
        importCardsService.importBySet(setCode);
    }

}
