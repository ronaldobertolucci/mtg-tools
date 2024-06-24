package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardImageStatus;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardRarity;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;
import br.com.bertolucci.mtgtools.shared.card.CardSearchParametersDto;
import br.com.bertolucci.mtgtools.shared.card.Color;
import br.com.bertolucci.mtgtools.shared.util.SqlOperator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class QueryFromCardSearchParameters {

    private CardSearchParametersDto cardParameters;
    private int limit;
    @Getter
    private String qlString;
    @Getter
    private TypedQuery<Card> query;

    public QueryFromCardSearchParameters(
            EntityManager em,
            CardSearchParametersDto cardSearchParametersDto,
            int limit
    ) {
        if (cardSearchParametersDto.format() == null || cardSearchParametersDto.format().isBlank()) {
            throw new IllegalArgumentException("O formato não pode ser nulo");
        }

        this.cardParameters = cardSearchParametersDto;
        this.limit = limit;

        createQuery(em);
    }

    private void createQuery(EntityManager entityManager) {
        addConditions();

        query = entityManager.createQuery(getQlString(), Card.class);

        setParameters();

        query.setFirstResult(0);
        query.setMaxResults(limit);
    }

    private void setParameters() {
        query.setParameter("format", DeckFormat.valueOf(cardParameters.format().trim().toUpperCase()));

        if (hasPower(cardParameters))  {
            addParameterToQuery("power", cardParameters.power().trim());
        }

        if (hasToughness(cardParameters)) {
            addParameterToQuery("toughness", cardParameters.toughness().trim());
        }

        if (hasLoyalty(cardParameters)) {
            addParameterToQuery("loyalty", cardParameters.loyalty().trim());
        }

        if (hasManaCost(cardParameters)) {
            addParameterToQuery("cost", cardParameters.manaCost().trim());
        }

        if (hasCmc(cardParameters)) {
            addParameterToQuery("cmc", cardParameters.cmc());
        }

        if (hasRarity(cardParameters)) {
            addParameterToQuery("rarity", CardRarity.valueOf(cardParameters.rarity().trim().toUpperCase()));
        }

        if (hasName(cardParameters)) {
            addParameterToQuery("name",cardParameters.name().trim());
        }

        if (onlyHighQuality(cardParameters)) {
            addParameterToQuery("image", CardImageStatus.HIGHRES_SCAN);
        }

        if (hasColors(cardParameters)) {
            for (SqlOperator key : cardParameters.colors().keySet()) {
                for (int i = 0; i < cardParameters.colors().get(key).size(); i++) {
                    addParameterToQuery("color" + i, cardParameters.colors().get(key).get(i).toString());
                }
            }
        }

        if (hasTypes(cardParameters)) {
            for (SqlOperator key : cardParameters.types().keySet()) {
                for (int i = 0; i < cardParameters.types().get(key).size(); i++) {
                    addParameterToQuery("type" + i, cardParameters.types().get(key).get(i));
                }
            }
        }

        if (hasKeywords(cardParameters)) {
            for (SqlOperator key : cardParameters.keywords().keySet()) {
                for (int i = 0; i < cardParameters.keywords().get(key).size(); i++) {
                    addParameterToQuery("keyword" + i, cardParameters.keywords().get(key).get(i));
                }
            }
        }
    }

    private void addConditions() {
        qlString = """
                SELECT c FROM Card c
                    JOIN FETCH c.legalities cl
                    LEFT JOIN FETCH c.faces f
                        WHERE cl.deckFormat = :format
                        AND (cl.legality = 'LEGAL' OR cl.legality = 'RESTRICTED')""";


        if (hasPower(cardParameters))  {
            addConditionToSqlString(SqlOperator.AND, "(c.power = :power OR f.power = :power)");
        }

        if (hasToughness(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND,"(c.toughness = :toughness OR f.toughness = :toughness)");
        }

        if (hasLoyalty(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, "(c.loyalty = :loyalty OR f.loyalty = :loyalty)");
        }

        if (hasManaCost(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, "(c.manaCost = :cost OR f.manaCost = :cost)");
        }

        if (hasCmc(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, "c.cmc = :cmc");
        }

        if (hasRarity(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, "c.cardRarity = :rarity");
        }

        if (hasName(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, "c.name ilike concat('%', :name, '%')");
        }

        if (onlyHighQuality(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, "c.imageStatus = :image");
        }

        if (hasColors(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, getColorsSqlString(cardParameters.colors()));
        }

        if (hasTypes(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, getTypesSqlString(cardParameters.types()));
        }

        if (hasKeywords(cardParameters)) {
            addConditionToSqlString(SqlOperator.AND, getKeywordsSqlString(cardParameters.keywords()));
        }
    }

    private void addConditionToSqlString(SqlOperator operator, String condition) {
        qlString += " " + operator.toString() + " " + condition;
    }

    private void addParameterToQuery(String parameter, Object value) {
        query.setParameter(parameter, value);
    }

    private String getColorsSqlString(Map<SqlOperator, List<Color>> colorsMap) {
        StringBuilder query = new StringBuilder("(");

        for (SqlOperator key : colorsMap.keySet()) {
            for (int i = 0; i < colorsMap.get(key).size(); i++) {
                if (i == 0) {
                    query.append("(c.manaCost ilike concat('%', :color")
                            .append(i)
                            .append(", '%') ")
                            .append("OR f.manaCost ilike concat('%', :color")
                            .append(i)
                            .append(", '%')) ");
                    continue;
                }

                query.append(key.toString())
                        .append(" (c.manaCost ilike concat('%', :color")
                        .append(i)
                        .append(", '%') ")
                        .append("OR f.manaCost ilike concat('%', :color")
                        .append(i)
                        .append(", '%')) ");
            }
        }

        query.append(")");
        return query.toString();
    }

    private String getTypesSqlString(Map<SqlOperator, List<String>> typesMap) {
        StringBuilder query = new StringBuilder("(");

        for (SqlOperator key : typesMap.keySet()) {
            for (int i = 0; i < typesMap.get(key).size(); i++) {
                if (i == 0) {
                    query.append("c.typeLine ilike concat('%', :type")
                            .append(i)
                            .append(", '%') ");
                    continue;
                }

                query.append(key.toString())
                        .append(" c.typeLine ilike concat('%', :type")
                        .append(i)
                        .append(", '%') ");
            }
        }

        query.append(")");
        return query.toString();
    }

    private String getKeywordsSqlString(Map<SqlOperator, List<String>> keywordsMap) {
        StringBuilder query = new StringBuilder("(");

        for (SqlOperator key : keywordsMap.keySet()) {
            for (int i = 0; i < keywordsMap.get(key).size(); i++) {
                if (i == 0) {
                    query.append("(c.oracleText ilike concat('%', :keyword")
                            .append(i)
                            .append(", '%') ")
                            .append("OR f.oracleText ilike concat('%', :keyword")
                            .append(i)
                            .append(", '%')) ");
                    continue;
                }

                query.append(key.toString())
                        .append(" (c.oracleText ilike concat('%', :keyword")
                        .append(i)
                        .append(", '%') ")
                        .append("OR f.oracleText ilike concat('%', :keyword")
                        .append(i)
                        .append(", '%')) ");
            }
        }

        query.append(")");
        return query.toString();
    }


    private boolean hasPower(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.power() != null && !cardSearchParametersDto.power().isBlank();
    }

    private boolean hasToughness(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.toughness() != null && !cardSearchParametersDto.toughness().isBlank();
    }

    private boolean hasLoyalty(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.loyalty() != null && !cardSearchParametersDto.loyalty().isBlank();
    }

    private boolean hasManaCost(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.manaCost() != null && !cardSearchParametersDto.manaCost().isBlank();
    }

    private boolean hasRarity(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.rarity() != null && !cardSearchParametersDto.rarity().isBlank();
    }

    private boolean hasCmc(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.cmc() != null && cardSearchParametersDto.cmc() >= 0.0;
    }

    private boolean hasName(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.name() != null && !cardSearchParametersDto.name().isBlank();
    }

    private boolean hasColors(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.colors() != null && !cardSearchParametersDto.colors().isEmpty();
    }

    private boolean hasTypes(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.types() != null && !cardSearchParametersDto.types().isEmpty();
    }

    private boolean hasKeywords(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.keywords() != null && !cardSearchParametersDto.keywords().isEmpty();
    }

    private boolean onlyHighQuality(CardSearchParametersDto cardSearchParametersDto) {
        return cardSearchParametersDto.onlyHighQuality() != null && cardSearchParametersDto.onlyHighQuality();
    }

}
