package br.com.bertolucci.mtgtools.deckbuilder.domain.util;

import java.util.regex.Pattern;

public class UUIDValidator {

    public static boolean isValid(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            return false;
        }

        Pattern UUID_REGEX =
                Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

        if (!UUID_REGEX.matcher(uuid).matches()) {
            return false;
        }

        return true;
    }

}
