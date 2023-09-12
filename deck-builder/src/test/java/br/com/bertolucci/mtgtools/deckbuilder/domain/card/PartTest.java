package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.PartDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    private Card card;
    private Part partOne;

    @BeforeEach
    void setUp() {
        card = new Card(
                new Set("t", "t", "core", 100, true, "test.com", "2020-01-01"),
                "en",
                "test",
                "normal",
                "black",
                "missing",
                "200",
                "name",
                0.0,
                true,
                "common",
                null);
        partOne = new Part(card, " tEsT nAmE ", " test type line", "tOkEn");
    }

    @Test
    void testPartConstructor() {
        assertEquals("test name", partOne.getName());
        assertEquals("test type line", partOne.getTypeLine());
        assertEquals(PartType.TOKEN, partOne.getType());
        assertNotNull(partOne.getCard());
    }

    @Test
    void testPartSecondaryConstructor() {
        Part part = new Part(new PartDto(" tEst naMe", "test type line ", "toKen "));
        assertEquals("test name", part.getName());
        assertEquals("test type line", part.getTypeLine());
        assertEquals(PartType.TOKEN, part.getType());
        assertNull(part.getCard());
    }

    @Test
    void testPartEquals() {
        Part partTwo = new Part(card, "test name ", " test type line", "token");
        assertNotSame(partOne, partTwo);
        assertEquals(partTwo, partTwo);
        assertNotEquals(partTwo, new Object());
        assertEquals(partOne, partTwo);
    }

    @Test
    void testPartHashCode() {
        assertEquals(173340535, partOne.hashCode());
    }

    @Test
    void testPartToString() {
        assertEquals("test name", partOne.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> new Part(card, input, " test type line", "token"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> partOne.setName(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenTypeLineIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> new Part(card, "name", input, "token"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenTypeLineIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> partOne.setTypeLine(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenPartTypeIsNotValidOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> new Part(card, "name", "a type line", input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenPartTypeIsNotValidOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> partOne.setType(input));
    }

}