package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.application.set.FindSetService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legality;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.shared.card.FaceDto;
import br.com.bertolucci.mtgtools.shared.card.PartDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CardFromDtoServiceTest {

    @Mock
    private CollectionService collectionService;
    @Mock
    private FindSetService findSetService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullSet(String setCode) {
        Card card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, setCode, null, null, null, null), null).get();
        assertNull(card.getSet());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testNullSetInDtoButNotNullInConstructor(String setCode) {
        Card card = new CardFromDtoService(collectionService, findSetService,
                getCardDto(null, setCode, null, null, null, null),
                new Set("test", "ttt", "core", 100, true, "test.com", "2020-01-01")).get();

        assertNotNull(card.getSet());
        assertEquals("test", card.getSet().getName());
    }

    @Test
    void testNotNullSet() {
        Mockito.when(findSetService.find(Mockito.any(String.class))).thenReturn(new Set());
        Card card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, "ttt", null, null, null, null), null).get();
        assertNotNull(card.getSet());
    }

    @ParameterizedTest
    @NullSource
    void testNullParts(List<PartDto> parts) {
        Card card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, null, null, parts, null, null), null).get();
        assertTrue(card.getParts().isEmpty());
    }

    @Test
    void testNotNullParts() {
        List<PartDto> partDtos = new ArrayList<>();
        partDtos.add(getPartDto());
        partDtos.add(getPartDto());
        partDtos.add(getPartDto());

        Card card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, null, null, partDtos, null, null), null).get();

        assertEquals(3, card.getParts().size());
    }

    @Test
    void testNotNullLegalities() {
        Map<String, String> legalities = new HashMap<>();
        legalities.put("standard", "legal");
        legalities.put("modern", "not_legal");
        legalities.put("brawl", "banned");
        legalities.put("alchemy", "legal");
        legalities.put("commander", "legal");
        legalities.put("explorer", "not_legal");
        legalities.put("historic", "not_legal");
        legalities.put("pioneer", "not_legal");
        legalities.put("pauper", "not_legal");
        legalities.put("oathbreaker", "not_legal");
        legalities.put("vintage", "not_legal");
        legalities.put("legacy", "not_legal");

        Card card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, null, null, null, null, legalities), null).get();

        assertEquals(Legality.LEGAL, card.getLegalities().getStandard());
        assertEquals(Legality.NOT_LEGAL, card.getLegalities().getModern());
        assertEquals(Legality.BANNED, card.getLegalities().getBrawl());
        assertEquals(Legality.LEGAL, card.getLegalities().getAlchemy());
        assertEquals(Legality.LEGAL, card.getLegalities().getCommander());
        assertEquals(Legality.NOT_LEGAL, card.getLegalities().getExplorer());
        assertEquals(Legality.NOT_LEGAL, card.getLegalities().getHistoric());
        assertEquals(Legality.NOT_LEGAL, card.getLegalities().getPioneer());
        assertEquals(Legality.NOT_LEGAL, card.getLegalities().getPauper());
        assertEquals(Legality.NOT_LEGAL, card.getLegalities().getOathBreaker());
        assertEquals(Legality.NOT_LEGAL, card.getLegalities().getVintage());
        assertEquals(Legality.NOT_LEGAL, card.getLegalities().getLegacy());
    }

    @Test
    void testNullLegalities() {
        Card card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, null, null, null, null, null), null).get();
        assertNotNull(card.getLegalities());

        Map<String, String> legalities = new HashMap<>();
        legalities.put("standard", null);
        legalities.put("modern", null);
        legalities.put("brawl", null);
        legalities.put("alchemy", null);
        legalities.put("commander", null);
        legalities.put("explorer", null);
        legalities.put("historic", null);
        legalities.put("pioneer", null);
        legalities.put("pauper", null);
        legalities.put("oathbreaker", null);
        legalities.put("vintage", null);
        legalities.put("legacy", null);

        card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, null, null, null, null, legalities), null).get();

        assertNull(card.getLegalities().getStandard());
        assertNull(card.getLegalities().getModern());
        assertNull(card.getLegalities().getBrawl());
        assertNull(card.getLegalities().getAlchemy());
        assertNull(card.getLegalities().getCommander());
        assertNull(card.getLegalities().getExplorer());
        assertNull(card.getLegalities().getHistoric());
        assertNull(card.getLegalities().getPioneer());
        assertNull(card.getLegalities().getPauper());
        assertNull(card.getLegalities().getOathBreaker());
        assertNull(card.getLegalities().getVintage());
        assertNull(card.getLegalities().getLegacy());
    }

    @ParameterizedTest
    @NullSource
    void testNullFaces(List<FaceDto> faceDtos) {
        Card card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, null, null, null, faceDtos, null), null).get();
        assertTrue(card.getFaces().isEmpty());
    }

    @Test
    void testNotNullFaces() {
        List<FaceDto> faceDtos = new ArrayList<>();
        faceDtos.add(getFaceDto());
        faceDtos.add(getFaceDto());

        Card card = new CardFromDtoService(
                collectionService, findSetService, getCardDto(null, null, null, null, faceDtos, null), null).get();

        assertEquals(2, card.getFaces().size());
    }

    private CardDto getCardDto(String artistName, String setCode, String[] keywords, List<PartDto> partDtos,
                               List<FaceDto> faceDtos, Map<String, String> legalities) {
        return new CardDto("black", "200", "missing", "common", false,
                keywords, "en", null, null, faceDtos, legalities, artistName, "a flavor",
                "a name", "3", "3", "a type", "normal", "3",
                "a oracle", "{3}", null, partDtos, setCode, 0.0, null, null, null);
    }

    private PartDto getPartDto() {
        return new PartDto("a name", "a type line", "token");
    }

    private FaceDto getFaceDto() {
        return new FaceDto("a flavor text ", "a Name ", "", null, "a Type Line ",
                "3 ", "{3}", "a oracle Text ", null, null, null, null, null);
    }


}