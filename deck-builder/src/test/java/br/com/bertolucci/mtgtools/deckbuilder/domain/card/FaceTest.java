package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.FaceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FaceTest {
    private Card card;
    private Face faceOne;

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
        faceOne = new Face(card, " tEst nAme ");
    }

    @Test
    void testFaceConstructor() {
        assertEquals("test name", faceOne.getName());
        assertNotNull(faceOne.getCard());
    }

    @Test
    void testFaceSecondaryConstructor() {
        Face face = new Face(new FaceDto("a flavor text ", "a Name ", "", null, "a Type Line ",
                "3 ", "{3}", "a oracle Text ", null, null, null, null, null));
        assertEquals("a flavor text ", face.getFlavorText());
        assertEquals("a name", face.getName());
        assertTrue(face.getToughness().isEmpty());
        assertNull(face.getPower());
        assertEquals("a Type Line ", face.getTypeLine());
        assertEquals("3 ", face.getLoyalty());
        assertEquals("{3}", face.getManaCost());
        assertEquals("a oracle Text ", face.getOracleText());
        assertNull(face.getOracleId());
        assertNull(face.getImageUri());
        assertNull(face.getCard());
    }

    @Test
    void testFaceEquals() {
        Face faceTwo = new Face(card, "test name ");
        assertNotSame(faceOne, faceTwo);
        assertEquals(faceTwo, faceTwo);
        assertNotEquals(faceTwo, new Object());
        assertEquals(faceOne, faceTwo);
    }

    @Test
    void testFaceHashCode() {
        assertEquals(-790022861, faceOne.hashCode());
    }

    @Test
    void testFaceToString() {
        assertEquals("test name", faceOne.toString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmptyOrNullOnConstructor(String input) {
        assertThrows(IllegalArgumentException.class, () -> new Face(card, input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmptyOrNullOnSetter(String input) {
        assertThrows(IllegalArgumentException.class, () -> faceOne.setName(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testWhenOracleIdIsNullOrEmptyOnSetter(String input) {
        faceOne.setOracleId(input);
        assertNull(faceOne.getOracleId());
    }

    @Test
    void throwsExceptionWhenOracleIdIsInvalidOnSetter() {
        assertThrows(IllegalArgumentException.class, () -> faceOne.setOracleId("invalid_uuid"));
    }

    @Test
    void testWhenOracleIdIsValidOnSetter() {
        faceOne.setOracleId("26929514-237c-11ed-861d-0242ac120002");
        assertEquals(UUID.fromString("26929514-237c-11ed-861d-0242ac120002"), faceOne.getOracleId());
    }
}