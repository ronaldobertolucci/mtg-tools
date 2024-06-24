package br.com.bertolucci.mtgtools.shared.util;

import java.util.*;

public class SplitStringSqlOperator {

    public static Map<SqlOperator, List<String>> split(String s) {

        if (s == null || s.isBlank()) return null;

        Map<SqlOperator, List<String>> map;
        map = new HashMap<>();

        if (s.contains("&&")) {
            String[] strings = s.split("&&");
            map.put(SqlOperator.AND, Arrays.stream(strings).toList());
            return map;
        }

        if (s.contains(";")) {
            String[] strings = s.split(";");
            map.put(SqlOperator.OR, Arrays.stream(strings).toList());
            return map;
        }

        List<String> words = new ArrayList<>();
        words.add(s);
        map.put(SqlOperator.AND, words);
        return map;
    }

}
