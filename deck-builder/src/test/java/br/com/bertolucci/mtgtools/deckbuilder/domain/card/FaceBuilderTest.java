package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.FaceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class FaceBuilderTest {

    private Face faceOne;
    private Face faceTwo;

    @BeforeEach
    void faceUp() {
        faceOne = new FaceBuilder(createFaceDto()).build();
        faceTwo = new FaceBuilder(createFaceDto()).build();
    }

    @Test
    void testFaceDtoConstructor() {
        assertEquals(faceOne.getName(), "Name test");
        assertEquals(faceOne.getToughness(), "1");
        assertEquals(faceOne.getPower(), "1");
        assertEquals(faceOne.getTypeLine(), "Type line test");
        assertEquals(faceOne.getLoyalty(), "1");
        assertEquals(faceOne.getManaCost(), "{1}{B}");
        assertEquals(faceOne.getOracleText(), "Oracle text test");
        assertNull(faceOne.getImageUri());
    }

    @Test
    void testFaceToString() {
        assertEquals(faceOne.toString(), "Name test");
    }

    @Test
    void testFaceEquals() {
        assertEquals(faceOne, faceTwo);
    }

    @Test
    void testFaceHashCode() {
        assertEquals(-1115303831, faceTwo.hashCode());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenNameIsEmpty(String input) {
        assertThrows(Exception.class, () -> new FaceBuilder(createFaceDto()).setName(input).build());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void throwsExceptionWhenTypeLineIsEmpty(String input) {
        assertThrows(Exception.class, () -> new FaceBuilder(createFaceDto()).setTypeLine(input)
                .build());
    }

    private FaceDto createFaceDto() {
        return new FaceDto("flavor example", "Name test", "1", "1",
                "Type line test", "1", "{1}{B}", "Oracle text test",
                null, null, null, null, null);
    }

}