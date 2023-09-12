package br.com.bertolucci.mtgtools.deckbuilder.domain.util;

import java.util.EnumSet;

public class EnumValidator {

    public static  <T extends Enum<T>> boolean isValid(String value, Class<T> var) {
        if (value == null) {
            return false;
        }

        return EnumSet.allOf(var)
                .stream()
                .anyMatch(e -> e.name().equalsIgnoreCase(value.trim()));
    }
}
