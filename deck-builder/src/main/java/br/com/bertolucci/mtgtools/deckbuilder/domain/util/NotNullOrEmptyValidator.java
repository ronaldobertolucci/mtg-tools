package br.com.bertolucci.mtgtools.deckbuilder.domain.util;

public class NotNullOrEmptyValidator {

    public static boolean isValid(String str) {
        return str != null && !str.trim().isEmpty();
    }

}
