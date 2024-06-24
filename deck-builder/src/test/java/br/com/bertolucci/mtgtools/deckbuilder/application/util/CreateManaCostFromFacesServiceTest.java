package br.com.bertolucci.mtgtools.deckbuilder.application.util;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CreateManaCostFromFacesServiceTest {

    @Test
    void testCardWithManaCost() {
        Card card = new Card();
        card.setManaCost("{R}");
        card.addFace(getFace("{U}"));
        card.addFace(getFace(null));

        CreateManaCostFromFacesService.create(card);

        assertEquals("{R}", card.getManaCost());
    }

    @ParameterizedTest
    @MethodSource("providingManaCosts")
    void testCardWithoutManaCost(String manaFaceOne, String manaFaceTwo, String result) {
        Card card = new Card();
        card.addFace(getFace(manaFaceOne));
        card.addFace(getFace(manaFaceTwo));

        CreateManaCostFromFacesService.create(card);

        assertEquals(result, card.getManaCost());
    }

    private static Stream<Arguments> providingManaCosts() {
        return Stream.of(
                Arguments.of("{U}", "{U}", "{U} // {U}"),
                Arguments.of(" {R} ", "{0}", "{R} // {0}"),
                Arguments.of(null, "{B}", "{B}"),
                Arguments.of("{W}", null, "{W}"),
                Arguments.of("", "{B}", "{B}"),
                Arguments.of("{W}", "", "{W}"),
                Arguments.of("{U}{R}{B}", "{U}", "{U}{R}{B} // {U}"),
                Arguments.of(" {R} ", "{0}{R}{B}", "{R} // {0}{R}{B}")
        );
    }

    private Face getFace(String manaCost) {
        Face face = new Face();
        face.setManaCost(manaCost);
        return face;
    }

}