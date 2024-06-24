package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.shared.card.CardSearchParametersDto;
import br.com.bertolucci.mtgtools.shared.card.Color;
import br.com.bertolucci.mtgtools.shared.util.SqlOperator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QueryFromCardSearchParametersTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery mockedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(em.createQuery(Mockito.any(), Mockito.any())).thenReturn(mockedQuery);
    }

    @Test
    public void testSQLStringForNamePowerToughnessLoyaltyCmcRarityAndQuality() {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        "name test",
                        null,
                        "mana test",
                        "power test",
                        "toughness test",
                        "loyalty test",
                        null,
                        null,
                        0.0,
                        "common",
                        "legacy",
                        true
                ), 10);

        assertEquals("""
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')\s""" +
                "AND (c.power = :power OR f.power = :power) " +
                "AND (c.toughness = :toughness OR f.toughness = :toughness) " +
                "AND (c.loyalty = :loyalty OR f.loyalty = :loyalty) " +
                "AND (c.manaCost = :cost OR f.manaCost = :cost) " +
                "AND c.cmc = :cmc " +
                "AND c.cardRarity = :rarity " +
                "AND c.name ilike concat('%', :name, '%') " +
                "AND c.imageStatus = :image",
                queryFromCardSearchParameters.getQlString());
    }

    @Test
    public void testSQLStringForColorsInOrCondition() {
        List<Color> colors = new ArrayList<>();
        colors.add(Color.B);
        colors.add(Color.U);

        Map<SqlOperator, List<Color>> mapColor = new HashMap<>();
        mapColor.put(SqlOperator.OR, colors);

        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        mapColor,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals("""
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')\s""" +
                "AND ((c.manaCost ilike concat('%', :color0, '%') OR f.manaCost ilike concat('%', :color0, '%')) " +
                "OR (c.manaCost ilike concat('%', :color1, '%') OR f.manaCost ilike concat('%', :color1, '%')) )",
                queryFromCardSearchParameters.getQlString());
    }

    @Test
    public void testSQLStringForColorsInAndCondition() {
        List<Color> colors = new ArrayList<>();
        colors.add(Color.B);
        colors.add(Color.U);

        Map<SqlOperator, List<Color>> mapColor = new HashMap<>();
        mapColor.put(SqlOperator.AND, colors);

        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        mapColor,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals("""
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')\s""" +
                "AND ((c.manaCost ilike concat('%', :color0, '%') OR f.manaCost ilike concat('%', :color0, '%')) " +
                "AND (c.manaCost ilike concat('%', :color1, '%') OR f.manaCost ilike concat('%', :color1, '%')) )",
                queryFromCardSearchParameters.getQlString());
    }

    @Test
    public void testSQLStringForKeywordsInOrCondition() {
        List<String> keywords = new ArrayList<>();
        keywords.add("flying");
        keywords.add("haste");

        Map<SqlOperator, List<String>> mapKeyword = new HashMap<>();
        mapKeyword.put(SqlOperator.OR, keywords);

        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        mapKeyword,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals("""
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')\s""" +
                "AND ((c.oracleText ilike concat('%', :keyword0, '%') OR f.oracleText ilike concat('%', :keyword0, '%')) " +
                "OR (c.oracleText ilike concat('%', :keyword1, '%') OR f.oracleText ilike concat('%', :keyword1, '%')) )",
                queryFromCardSearchParameters.getQlString());
    }

    @Test
    public void testSQLStringForKeywordsInAndCondition() {
        List<String> keywords = new ArrayList<>();
        keywords.add("flying");
        keywords.add("haste");

        Map<SqlOperator, List<String>> mapKeyword = new HashMap<>();
        mapKeyword.put(SqlOperator.AND, keywords);

        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        mapKeyword,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals("""
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')\s""" +
                    "AND ((c.oracleText ilike concat('%', :keyword0, '%') OR f.oracleText ilike concat('%', :keyword0, '%')) " +
                    "AND (c.oracleText ilike concat('%', :keyword1, '%') OR f.oracleText ilike concat('%', :keyword1, '%')) )",
                queryFromCardSearchParameters.getQlString());
    }

    @Test
    public void testSQLStringForTypesInOrCondition() {
        List<String> types = new ArrayList<>();
        types.add("warrior");
        types.add("human");

        Map<SqlOperator, List<String>> mapType = new HashMap<>();
        mapType.put(SqlOperator.OR, types);

        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        mapType,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals("""
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')\s""" +
                "AND (c.typeLine ilike concat('%', :type0, '%') OR c.typeLine ilike concat('%', :type1, '%') )",
                queryFromCardSearchParameters.getQlString());
    }

    @Test
    public void testSQLStringForTypesInAndCondition() {
        List<String> types = new ArrayList<>();
        types.add("warrior");
        types.add("human");

        Map<SqlOperator, List<String>> mapType = new HashMap<>();
        mapType.put(SqlOperator.AND, types);

        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        mapType,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals("""
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')\s""" +
                        "AND (c.typeLine ilike concat('%', :type0, '%') AND c.typeLine ilike concat('%', :type1, '%') )",
                queryFromCardSearchParameters.getQlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenNameIsNullOrEmpty(String nameInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        nameInput,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenTypeIsNullOrEmpty(Map<SqlOperator, List<String>> typeInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        typeInput,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenManaIsNullOrEmpty(String manaInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        manaInput,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenPowerIsNullOrEmpty(String powerInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        powerInput,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenToughnessIsNullOrEmpty(String toughnessInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        toughnessInput,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenLoyaltyIsNullOrEmpty(String loyaltyInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        loyaltyInput,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenKeywordsIsNullOrEmpty(Map<SqlOperator, List<String>> keywordsInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        keywordsInput,
                        null,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenColorsIsNullOrEmpty(Map<SqlOperator, List<Color>> colorsInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        colorsInput,
                        null,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -1000})
    public void testInitialSQLStringWhenCmcIsNull(Double cmcInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        cmcInput,
                        null,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testInitialSQLStringWhenRarityIsNullOrEmpty(String rarityInput) {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        rarityInput,
                        "legacy",
                        null
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @Test
    public void testInitialSQLStringWhenQualityIsFalse() {
        QueryFromCardSearchParameters queryFromCardSearchParameters = new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "legacy",
                        false
                ), 10);

        assertEquals(queryFromCardSearchParameters.getQlString(), getInitialSqlString());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void throwsExceptionWhenFormatIsNullOrEmpty(String input) {
        assertThrows(IllegalArgumentException.class, () -> new QueryFromCardSearchParameters(
                em,
                new CardSearchParametersDto(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    input,
                    null
        ), 10));
    }

    private String getInitialSqlString() {
        return """
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')""";
    }
}