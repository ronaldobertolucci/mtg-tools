package br.com.bertolucci.mtgtools.deckbuilder.domain.symbol;

import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTest {

    private Symbol symbolOne;
    private Symbol symbolTwo;

    @BeforeEach
    void symbolUp() {
        symbolOne = new Symbol(" {U} ", "a deScriPtion ", true, 1.5,
                "http://test.com");
    }

    @Test
    void testSymbolConstructor() {
        assertEquals("{U}", symbolOne.getSymbol());
        assertEquals("a description", symbolOne.getDescription());
        assertTrue(symbolOne.getRepresentsMana());
        assertEquals(1.5, symbolOne.getManaValue());
        assertEquals("http://test.com", symbolOne.getImageUri());
    }

    @Test
    void testSymbolSecondaryConstructor() {
        Symbol symbol = new Symbol(new SymbolDto(" {U} ", "a deScriPtion ", true,
                1.5, "http://test.com"));
        assertEquals("{U}", symbolOne.getSymbol());
        assertEquals("a description", symbolOne.getDescription());
        assertTrue(symbolOne.getRepresentsMana());
        assertEquals(1.5, symbolOne.getManaValue());
        assertEquals("http://test.com", symbolOne.getImageUri());
    }

    @Test
    void testSymbolEquals() {
        symbolTwo = new Symbol(" {U} ", "a deScriPtion ", true, 1.5,
                "https://test.com");
        assertNotSame(symbolOne, symbolTwo);
        assertEquals(symbolOne, symbolOne);
        assertNotEquals(symbolOne, new Object());
        assertEquals(symbolOne, symbolTwo);
    }

    @Test
    void testSymbolHashCode() {
        assertEquals(121022, symbolOne.hashCode());
    }

    @Test
    void testSymbolToString() {
        assertEquals("{U}", symbolOne.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSymbolIsNullOrEmptyOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Symbol(
                input, "a deScriPtion ", true, 1.5, "https://test.com"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSymbolIsNullOrEmptyOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> symbolOne.setSymbol(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSymbolDescriptionIsNullOrEmptyOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Symbol(
                "{U}", input, true, 1.5, "https://test.com"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSymbolDescriptionIsNullOrEmptyOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> symbolOne.setDescription(input));
    }

    @ParameterizedTest
    @NullSource
    void throwsExceptionWhenSymbolRepresentsManaIsNullOnConstructor(Boolean input) {
        assertThrows(IllegalArgumentException.class, () -> new Symbol(
                "{U}", "a description", input, 1.5, "https://test.com"));
    }

    @ParameterizedTest
    @NullSource
    void throwsExceptionWhenSymbolRepresentsManaIsNullOnSetter(Boolean input) {
        assertThrows(IllegalArgumentException.class, () -> symbolOne.setRepresentsMana(input));
    }

}