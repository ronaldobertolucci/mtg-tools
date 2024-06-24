package br.com.bertolucci.mtgtools.deckbuilder.domain.symbol;

import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class SymbolBuilderTest {

    private Symbol symbolOne;
    private Symbol symbolTwo;

    @BeforeEach
    void setUp() {
        symbolOne = new SymbolBuilder(createSymbolDto()).build();
        symbolTwo = new SymbolBuilder(createSymbolDto()).build();
    }

    @Test
    void testSymbolDtoConstructor() {
        assertEquals(symbolOne.getSymbol(), "Symbol test");
        assertEquals(symbolOne.getDescription(), "Description test");
        assertEquals(symbolOne.getRepresentsMana(), true);
        assertEquals(symbolOne.getManaValue(), 2.0);
        assertEquals(symbolOne.getImageUri(), "test.com.br");
    }

    @Test
    void testSymbolToString() {
        assertEquals(symbolOne.toString(), "Symbol test");
    }

    @Test
    void testSymbolEquals() {
        assertEquals(symbolOne, symbolTwo);
    }

    @Test
    void testSymbolHashCode() {
        assertEquals(1279993397, symbolTwo.hashCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSymbolIsEmpty(String input) {
        assertThrows(Exception.class, () -> new SymbolBuilder(createSymbolDto()).setSymbol(input).build());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenDescriptionIsEmpty(String input) {
        assertThrows(Exception.class, () -> new SymbolBuilder(createSymbolDto()).setDescription(input).build());
    }

    @ParameterizedTest
    @NullSource
    void throwsExceptionWhenRepresentsManaIsNull(Boolean input) {
        assertThrows(Exception.class, () -> new SymbolBuilder(createSymbolDto()).setRepresentsMana(input).build());
    }

    private SymbolDto createSymbolDto() {
        return new SymbolDto("Symbol test", "Description test", true,
                2.0, "test.com.br");
    }

}