package br.com.bertolucci.mtgtools.shared.card;

import br.com.bertolucci.mtgtools.shared.util.SqlOperator;

import java.util.List;
import java.util.Map;

public record CardSearchParametersDto(
        String name,
        Map<SqlOperator, List<String>> types,
        String manaCost,
        String power,
        String toughness,
        String loyalty,
        Map<SqlOperator, List<String>> keywords,
        Map<SqlOperator, List<Color>> colors,
        Double cmc,
        String rarity,
        String format,
        Boolean onlyHighQuality
) {
}
