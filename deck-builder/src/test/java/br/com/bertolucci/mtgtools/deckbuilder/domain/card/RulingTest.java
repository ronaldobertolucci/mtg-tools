package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.RulingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RulingTest {

    private Card card;
    private Ruling rulingOne;

    @BeforeEach
    void setUp() {
        card = new Card();
        rulingOne = new Ruling(card, " wTc ", "2020-10-10", " a cOmmeNt ");
    }

    @Test
    void testRulingConstructor() {
        assertEquals("wtc", rulingOne.getSource());
        assertEquals(LocalDate.parse("2020-10-10"), rulingOne.getPublishedAt());
        assertEquals("a comment", rulingOne.getComment());
        assertNotNull(rulingOne.getCard());
    }

    @Test
    void testRulingSecondaryConstructor() {
        Ruling ruling = new Ruling(new RulingDto(" wTc", "2020-10-10", "a cOmmeNt "));
        assertEquals("wtc", ruling.getSource());
        assertEquals(LocalDate.parse("2020-10-10"), ruling.getPublishedAt());
        assertEquals("a comment", ruling.getComment());
        assertNull(ruling.getCard());
    }

    @Test
    void testRulingEquals() {
        Ruling rulingTwo = new Ruling(card, "wtc ", "2020-10-10", "a comment ");
        assertNotSame(rulingOne, rulingTwo);
        assertEquals(rulingTwo, rulingTwo);
        assertNotEquals(rulingTwo, new Object());
        assertEquals(rulingOne, rulingTwo);
    }

    @Test
    void testRulingHashCode() {
        assertEquals(-1983366297, rulingOne.hashCode());
    }

    @Test
    void testRulingToString() {
        assertEquals("a comment", rulingOne.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSourceIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> new Ruling(card, input, "2020-10-10", "a comment "));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenSourceIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> rulingOne.setSource(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenCommentIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> new Ruling(card, "wtc", "2020-10-10", input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenCommentIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> rulingOne.setComment(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"10/10/2020", "2020/10/10"})
    void throwsExceptionWhenPublishedAtIsEmptyNullOrInvalidOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class,
                () -> new Ruling(card, "wtc", input, "comment"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"10/10/2020", "2020/10/10"})
    void throwsExceptionWhenPublishedAtIsEmptyNullOrInvalidOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> rulingOne.setPublishedAt(input));
    }

}