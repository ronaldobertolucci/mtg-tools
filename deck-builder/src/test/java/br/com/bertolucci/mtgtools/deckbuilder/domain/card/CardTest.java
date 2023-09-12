package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Set set;
    private Card cardOne;

    @BeforeEach
    void setUp() {
        set = new Set("t", "t", "core", 100, true, "test.com", "2020-01-01");

        cardOne = new Card(
                set,
                " eN",
                " test Type",
                "normAl ",
                " blAck",
                "missIng ",
                "200 ",
                " tEsT nAmE ",
                0.0,
                true,
                " cOmmon",
                "test.com");
    }

    @Test
    void testCardConstructor() {
        assertEquals(Lang.EN, cardOne.getLang());
        assertEquals("test Type", cardOne.getTypeLine());
        assertEquals(Layout.NORMAL, cardOne.getLayout());
        assertEquals(BorderColor.BLACK, cardOne.getBorderColor());
        assertEquals(ImageStatus.MISSING, cardOne.getImageStatus());
        assertEquals("200", cardOne.getCollectorNumber());
        assertEquals("test name", cardOne.getName());
        assertTrue(cardOne.isDigital());
        assertEquals(Rarity.COMMON, cardOne.getRarity());
        assertEquals("test.com", cardOne.getImageUri());
        assertEquals(0.0, cardOne.getCmc());
    }

    @Test
    void testCardSecondaryConstructor() {
        Card card = new Card(new CardDto("bLack", "200", "Missing ", "mythiC ",
                true, null, "eN ", null, null, null, null, null,
                "A flavor text", "A naMe ", "3", "2", "A type Line ", "sPlit",
                "3", "A oracle Text", "{2}", null, null, null, 0.0, null, null, null));
        assertEquals(BorderColor.BLACK, card.getBorderColor());
        assertEquals("200", card.getCollectorNumber());
        assertEquals(ImageStatus.MISSING, card.getImageStatus());
        assertEquals(Rarity.MYTHIC, card.getRarity());
        assertTrue(card.isDigital());
        assertEquals(Lang.EN, card.getLang());
        assertNull(card.getOracleId());
        assertEquals("A flavor text", card.getFlavorText());
        assertEquals("a name", card.getName());
        assertEquals("3", card.getToughness());
        assertEquals("2", card.getPower());
        assertEquals("3", card.getLoyalty());
        assertEquals("A oracle Text", card.getOracleText());
        assertEquals("{2}", card.getManaCost());
        assertNull(card.getImageUri());
        assertNull(card.getRulingsUri());
        assertNull(card.getSet());
        assertEquals("A type Line", card.getTypeLine());
        assertEquals(Layout.SPLIT, card.getLayout());
        assertEquals(0.0, card.getCmc());
    }

    @Test
    void testCardEquals() {
        Card cardTwo = new Card(
                set,
                "en",
                " test type",
                "normal",
                "black",
                "missing",
                "200",
                "test name ",
                0.0,
                true,
                "common",
                null);
        assertNotSame(cardOne, cardTwo);
        assertEquals(cardTwo, cardTwo);
        assertNotEquals(cardTwo, new Object());
        assertEquals(cardOne, cardTwo);
    }

    @Test
    void testCardHashCode() {
        assertEquals(-478595351, cardOne.hashCode());
    }

    @Test
    void testCardToString() {
        assertEquals("test name", cardOne.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                "en",
                " test type",
                "normal",
                "black",
                "missing",
                "200",
                input,
                0.0,
                true,
                "common",
                null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setName(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenTypeLineIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                "en",
                input,
                "normal",
                "black",
                "missing",
                "200",
                "name",
                0.0,
                true,
                "common",
                null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenTypeLineIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setTypeLine(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenCollectorNumberIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                "en",
                "type",
                "normal",
                "black",
                "missing",
                input,
                "name",
                0.0,
                true,
                "common",
                null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenCollectorNumberIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setCollectorNumber(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid_uuid"})
    void throwsExceptionWhenOracleIdIsInvalidOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setOracleId(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testWhenOracleIdIsNullOrEmptyOnSetter(String input) {
        Card card = new Card();
        card.setOracleId(input);
        assertNull(card.getOracleId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"portugues, ingles, espanhol"})
    void throwsExceptionWhenLangIsInvalidOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                input,
                "type",
                "normal",
                "black",
                "missing",
                "200",
                "name",
                0.0,
                true,
                "common",
                null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"portugues, ingles, espanhol"})
    void throwsExceptionWhenLangIsInvalidOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setLang(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"grande, pequeno"})
    void throwsExceptionWhenLayoutIsInvalidOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                "en",
                "type",
                input,
                "black",
                "missing",
                "200",
                "name",
                0.0,
                true,
                "common",
                null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"grande, pequeno"})
    void throwsExceptionWhenLayoutIsInvalidOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setLayout(input));
    }


    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"branco, preto"})
    void throwsExceptionWhenBorderColorIsInvalidOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                "en",
                "type",
                "normal",
                input,
                "missing",
                "200",
                "name",
                0.0,
                true,
                "common",
                null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"branco, preto"})
    void throwsExceptionWhenBorderColorIsInvalidOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setBorderColor(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"ruim, bom"})
    void throwsExceptionWhenImageStatusIsInvalidOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                "en",
                "type",
                "normal",
                "black",
                input,
                "200",
                "name",
                0.0,
                true,
                "common",
                null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"ruim, bom"})
    void throwsExceptionWhenImageStatusIsInvalidOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setImageStatus(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"comum, mítico"})
    void throwsExceptionWhenRarityIsInvalidOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                "en",
                "type",
                "normal",
                "black",
                "missing",
                "200",
                "name",
                0.0,
                true,
                input,
                null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"comum, mítico"})
    void throwsExceptionWhenRarityIsInvalidOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setRarity(input));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {-1.0, -100.0})
    void throwsExceptionWhenCmcIsInvalidOnConstructor(Double input) {
        assertThrows(IllegalArgumentException.class, () -> new Card(
                set,
                "en",
                "type",
                "normal",
                "black",
                "missing",
                "200",
                "name",
                input,
                true,
                "common",
                null));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {-1.0, -100.0})
    void throwsExceptionWhenCmcIsInvalidOnSetter(Double input) {
        assertThrows(IllegalArgumentException.class, () -> cardOne.setCmc(input));
    }


}