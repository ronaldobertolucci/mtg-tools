package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

import br.com.bertolucci.mtgtools.shared.set.SetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SetTest {

    private Set setOne;
    private Set setTwo;

    @BeforeEach
    void setUp() {
        setOne = new Set(
                " tEsT name ",
                " tEsT cOdE",
                "cOrE ",
                100,
                true,
                "http://test.com.br",
                "2020-01-01");
    }

    @Test
    void testSetConstructor() {
        assertEquals("test name", setOne.getName());
        assertEquals("test code", setOne.getCode());
        assertEquals(SetType.CORE, setOne.getType());
        assertEquals(100, setOne.getTotalCards());
        assertEquals("http://test.com.br", setOne.getImageUri());
        assertTrue(setOne.isDigital());
        assertEquals(LocalDate.parse("2020-01-01"), setOne.getReleasedAt());
    }

    @Test
    void testSetSecondaryConstructor() {
        Set set = new Set(new SetDto(" tEsT cOdE ", " tEsT name", " Core",
                100, true, "test.com", "2020-01-01", null, null));
        assertEquals("test name", set.getName());
        assertEquals("test code", set.getCode());
        assertEquals(SetType.CORE, set.getType());
        assertEquals(100, set.getTotalCards());
        assertTrue(set.isDigital());
        assertEquals("test.com", set.getImageUri());
        assertEquals(LocalDate.parse("2020-01-01"), set.getReleasedAt());
    }

    @Test
    void testSetEquals() {
        setTwo = new Set(
                "test name ",
                " test code",
                "core",
                100,
                true,
                "http://test.com.br",
                "2020-01-01");

        assertNotSame(setOne, setTwo);
        assertEquals(setOne, setOne);
        assertNotEquals(setOne, new Object());
        assertEquals(setOne, setTwo);
    }

    @Test
    void testSetHashCode() {
        assertEquals(-977166765, setOne.hashCode());
    }

    @Test
    void testSetToString() {
        assertEquals("test name", setOne.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetNameIsNullOrEmptyOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Set(
                input,
                " test code",
                "core",
                100,
                true,
                "http://test.com.br",
                "2020-01-01"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetNameIsNullOrEmptyOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> setOne.setName(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetCodeIsNullOrEmptyOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Set(
                "test name",
                input,
                "core",
                100,
                true,
                "http://test.com.br",
                "2020-01-01"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetCodeIsNullOrEmptyOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> setOne.setCode(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void throwsExceptionWhenSetTotalCardsIsLowerThanOneOnConstructor(int input) {
        assertThrows(IllegalArgumentException.class, () -> new Set(
                "test name",
                " test code",
                "core",
                input,
                true,
                "http://test.com.br",
                "2020-01-01"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
    void throwsExceptionWhenSetTotalCardsIsLowerThanOneOnSetter(int input) {
        assertThrows(IllegalArgumentException.class, () -> setOne.setTotalCards(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetTypeNotExistsOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Set(
                "test name",
                " test code",
                input,
                100,
                true,
                "http://test.com.br",
                "2020-01-01"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetTypeNotExistsOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> setOne.setType(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetImageUriIsNullOrEmptyOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Set(
                "test name",
                "code",
                "core",
                100,
                true,
                input,
                "2020-01-01"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSetImageUriIsNullOrEmptyOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> setOne.setImageUri(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10/01/2020", "2020/10/10"})
    void testReleasedDateWhenSetReleasedAtIsNullEmptyOrNotValidOnSetter(String input) {
        setOne.setReleasedAt(input);
        assertNull(setOne.getReleasedAt());
    }

}