package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerifyCardCollectionServiceTest {

    @Mock
    private CollectionService collectionService;
    private VerifyCardCollectionService verifyCardCollectionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testNoCardsInCollection() {
        Mockito.when(collectionService.findCardsBySet(Mockito.any())).thenReturn(new ArrayList<>());

        List<CardDto> list = new VerifyCardCollectionService(getCardDtos(), collectionService, getSet()).returnNonDuplicated();

        assertEquals(3, list.size());
    }

    @Test
    void testCardsInCollection() {
        Mockito.when(collectionService.findCardsBySet(Mockito.any())).thenReturn(getCards());

        List<CardDto> list = new VerifyCardCollectionService(getCardDtos(), collectionService, getSet()).returnNonDuplicated();

        assertTrue(list.isEmpty());
    }

    private List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(getCardDto("a name", "1")));
        cards.add(new Card(getCardDto("a name two", "2")));
        cards.add(new Card(getCardDto("a name three", "3")));

        for (Card card: cards) {
            card.setSet(getSet());
        }

        return cards;
    }

    private List<CardDto> getCardDtos() {
        List<CardDto> cardDtos = new ArrayList<>();
        cardDtos.add(getCardDto("a name", "1"));
        cardDtos.add(getCardDto("a name two", "2"));
        cardDtos.add(getCardDto("a name three", "3"));
        return cardDtos;
    }

    private Set getSet() {
        return new Set("test", "ttt", "core", 3, "test.com", "2020-01-01");
    }

    private CardDto getCardDto(String name, String collectorNumber) {
        return new CardDto("black", collectorNumber, "missing", "common", false,
                null, "en", null, null, null, null, "rony",
                "a flavor", name, "3", "3", "a type", "normal",
                "3", "a oracle", "{3}", null, null, "ttt", 0.0, null, null, null);
    }

}