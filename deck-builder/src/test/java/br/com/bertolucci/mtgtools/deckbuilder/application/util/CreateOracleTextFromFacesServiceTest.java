package br.com.bertolucci.mtgtools.deckbuilder.application.util;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CreateOracleTextFromFacesServiceTest {

    @Test
    void testCardWithOracleText() {
        Card card = new Card();
        card.setOracleText("A oracle Text");
        card.addFace(getFace("A new oracle text"));
        card.addFace(getFace(null));

        CreateOracleTextFromFacesService.create(card);

        assertEquals("A new oracle text", card.getOracleText());
    }

    @ParameterizedTest
    @MethodSource("providingOracleTexts")
    void testCardWithoutOracleText(String textFaceOne, String textFaceTwo, String result) {
        Card card = new Card();
        card.addFace(getFace(textFaceOne));
        card.addFace(getFace(textFaceTwo));

        CreateOracleTextFromFacesService.create(card);

        assertEquals(result, card.getOracleText());
    }

    private static Stream<Arguments> providingOracleTexts() {
        return Stream.of(
                Arguments.of("face one text", "Face two text", "face one text\n\n//\n\nFace two text"),
                Arguments.of(" face one text ", "Face two text", "face one text\n\n//\n\nFace two text"),
                Arguments.of(null, "Face two text", "Face two text"),
                Arguments.of("face one text", null, "face one text"),
                Arguments.of("", "Face two text", "Face two text"),
                Arguments.of("face one text", "", "face one text")
        );
    }

    private Face getFace(String oracleText) {
        Face face = new Face();
        face.setOracleText(oracleText);
        return face;
    }

}