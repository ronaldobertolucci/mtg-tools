package br.com.bertolucci.mtgtools.deckbuilder.domain.deck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deckOne;

    @BeforeEach
    void setUp() {
        deckOne = new Deck(" sTaNdarD ", " A dEck Name");
    }

    @Test
    void testDeckConstructor() {
        assertSame(Format.STANDARD, deckOne.getFormat());
        assertEquals("a deck name", deckOne.getName());
    }

    @Test
    void testDeckEquals() {
        Deck deckTwo = new Deck("standard", "a deck name ");
        assertNotSame(deckOne, deckTwo);
        assertEquals(deckTwo, deckTwo);
        assertNotEquals(deckTwo, new Object());
        assertEquals(deckOne, deckTwo);
    }

    @Test
    void testDeckHashCode() {
        assertEquals(134074078, deckOne.hashCode());
    }

    @Test
    void testDeckToString() {
        assertEquals("a deck name: " + Format.STANDARD, deckOne.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Deck("standard", input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> deckOne.setName(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenFormatIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Deck(input, "name"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenFormatIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> deckOne.setFormat(input));
    }

}