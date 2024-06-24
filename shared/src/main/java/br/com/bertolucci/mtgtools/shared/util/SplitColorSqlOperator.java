package br.com.bertolucci.mtgtools.shared.util;

import br.com.bertolucci.mtgtools.shared.card.Color;

import java.util.*;

public class SplitColorSqlOperator {

    public static Map<SqlOperator, List<Color>> split(String s) {

        if (s == null || s.isBlank()) return null;

        Map<SqlOperator, List<Color>> map;
        map = new HashMap<>();
        List<Color> colors = new ArrayList<>();

        if (s.contains("&&")) {
            String[] strings = s.split("&&");
            for (String s1: strings) {
                colors.add(Color.valueOf(s1.trim().toUpperCase()));
            }
            map.put(SqlOperator.AND, colors);
            return map;
        }

        if (s.contains(";")) {
            String[] strings = s.split(";");
            for (String s1: strings) {
                colors.add(Color.valueOf(s1.trim().toUpperCase()));
            }
            map.put(SqlOperator.OR, colors);
            return map;
        }

        colors.add(Color.valueOf(s.trim().toUpperCase()));
        map.put(SqlOperator.AND, colors);
        return map;
    }

}
