package br.com.bertolucci.mtgtools.deckbuilder;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.card.CardSearchParametersDto;

import java.time.LocalDate;
import java.util.List;

public interface DeckBuilderService {

    List<Set> getSets();

    List<Symbol> getSymbols();

    List<Deck> getDecks();

    void saveDeck(Deck deck);

    void updateDeck(Deck deck);

    void removeDeck(Deck deck);

    void addCardToDeck(CardDeck cardDeck, boolean isRelentless);

    void removeCardFromDeck(CardDeck cardDeck);

    void downloadCardImage(Card card);

    List<Card> findCardsByParameter(CardSearchParametersDto cardSearchParametersDto, int limit);

    List<Card> findCardsBySet(Integer setId);

    void updateLegalities() throws NoApiConnectionException, InterruptedException;

    LocalDate lastUpdate();
}
