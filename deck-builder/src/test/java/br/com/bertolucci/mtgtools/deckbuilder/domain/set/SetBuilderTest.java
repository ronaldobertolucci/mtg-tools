package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

import br.com.bertolucci.mtgtools.shared.set.SetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SetBuilderTest {
    
    private Set setOne;
    private Set setTwo;

    @BeforeEach
    void setUp() {
        setOne = new SetBuilder(createSetDto()).build();
        setTwo = new SetBuilder(createSetDto()).build();
    }

    @Test
    void testSetDtoConstructor() {
        assertEquals(setOne.getCode(), "ttt");
        assertEquals(setOne.getName(), "Name test");
        assertEquals(setOne.getParentSet(), "ccc");
        assertEquals(setOne.getType(), SetType.CORE);
        assertEquals(setOne.getTotalCards(), 10);
        assertEquals(setOne.getReleasedAt(), LocalDate.parse("2024-01-01"));
        assertEquals(setOne.getImageUri(), "test.com.br");
    }

    @Test
    void testSetToString() {
        assertEquals(setOne.toString(), "Name test");
    }

    @Test
    void testSetEquals() {
        assertEquals(setOne, setTwo);
    }

    @Test
    void testSetHashCode() {
        assertEquals(115247, setTwo.hashCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetCodeIsEmpty(String input) {
        assertThrows(Exception.class, () -> new SetBuilder(createSetDto()).setCode(input).build());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmpty(String input) {
        assertThrows(Exception.class, () -> new SetBuilder(createSetDto()).setName(input).build());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenImageUriIsEmpty(String input) {
        assertThrows(Exception.class, () -> new SetBuilder(createSetDto()).setImageUri(input).build());
    }

    private SetDto createSetDto() {
        return new SetDto("ttt", "Name test", "core", 10, true,
                "test.com.br", "2024-01-01", "test.com.br", "ccc");
    }

}