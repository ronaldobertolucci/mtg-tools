package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.shared.card.FaceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CardBuilderTest {

    private Card cardOne;
    private Card cardTwo;

    @BeforeEach
    void cardUp() {
        cardOne = new CardBuilder(createCardDto(), createSet()).build();
        cardTwo = new CardBuilder(createCardDto(), createSet()).build();
    }

    @Test
    void testCardDtoConstructor() {
        assertEquals(cardOne.getSet(), createSet());
        assertEquals(cardOne.getTypeLine(), "Type line test");
        assertEquals(cardOne.getImageStatus(), CardImageStatus.MISSING);
        assertEquals(cardOne.getCollectorNumber(), "123");
        assertEquals(cardOne.getName(), "Name test");
        assertEquals(cardOne.getIsDigital(), true);
        assertEquals(cardOne.getCardRarity(), CardRarity.COMMON);
        assertEquals(cardOne.getToughness(), "1");
        assertEquals(cardOne.getPower(), "1");
        assertEquals(cardOne.getLoyalty(), "1");
        assertEquals(cardOne.getOracleText(), "Oracle text test\n\n//\n\nOracle text test");
        assertEquals(cardOne.getManaCost(), "{2}{B}");
        assertEquals(cardOne.getCmc(), 3.0);
        assertNull(cardOne.getImageUri());

        assertEquals(cardOne.getFaces().size(), 2);
        assertEquals(cardOne.getLegalities().size(), 1);
        assertEquals(cardOne.getLegalities().stream().toList().get(0),
                new CardLegality(cardOne, Legality.LEGAL, DeckFormat.STANDARD));
    }

    @Test
    void testCardToString() {
        assertEquals(cardOne.toString(), "Name test");
    }

    @Test
    void testCardEquals() {
        assertEquals(cardOne, cardTwo);
    }

    @Test
    void testCardHashCode() {
        assertEquals(-977342775, cardTwo.hashCode());
    }

    @ParameterizedTest
    @NullSource
    void throwsExceptionWhenSetIsNull(Set input) {
        assertThrows(Exception.class, () -> new CardBuilder(createCardDto(), input).build());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmpty(String input) {
        assertThrows(Exception.class, () -> new CardBuilder(createCardDto(), createSet()).setName(input).build());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenCollectorNumberIsEmpty(String input) {
        assertThrows(Exception.class, () -> new CardBuilder(createCardDto(), createSet()).setCollectorNumber(input)
                .build());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -100.0})
    void throwsExceptionWhenCmcIsLowerThanZero(Double input) {
        assertThrows(Exception.class, () -> new CardBuilder(createCardDto(), createSet()).setCmc(input).build());
    }

    private Set createSet() {
        Set set = new Set();
        set.setCode("ttt");
        return set;
    }

    private CardDto createCardDto() {
        return new CardDto("123", "missing", "common", true, createFaces(),
                createLegalities(), "Name test", "1", "1", "Type line test",
                "1", "Oracle text test", "{2}{B}", null, "ttt", 3.0);
    }

    private List<FaceDto> createFaces() {
        List<FaceDto> faces = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            faces.add(new FaceDto("flavor example", "Name test", "1", "1",
                    "Type line test", null, "{1}{B}", "Oracle text test",
                    null, null, null, null, null));
        }
        return faces;
    }

    private Map<String, String> createLegalities() {
        Map<String, String> legalities = new HashMap<>();
        legalities.put("standard", "legal");
        legalities.put("explorer", "not_legal");
        return legalities;
    }

}