package br.com.bertolucci.mtgtools.deckbuilder.domain.deck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class DeckBuilderTest {

    private Deck deckOne;
    private Deck deckTwo;

    @BeforeEach
    void deckUp() {
        deckOne = new DeckBuilder("Name test", "standard").build();
        deckTwo = new DeckBuilder("Name test", "Standard").build();
    }

    @Test
    void testDeckDtoConstructor() {
        assertEquals(deckOne.getName(), "Name test");
        assertEquals(deckOne.getDeckFormat(), DeckFormat.STANDARD);
    }

    @Test
    void testDeckToString() {
        assertEquals(deckOne.toString(), "Name test: padrão (standard)");
    }

    @Test
    void testDeckEquals() {
        assertEquals(deckOne, deckTwo);
    }

    @Test
    void testDeckHashCode() {
        assertEquals(-1115309790, deckTwo.hashCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmpty(String input) {
        assertThrows(Exception.class, () -> new DeckBuilder(input, "standard").build());
    }
}