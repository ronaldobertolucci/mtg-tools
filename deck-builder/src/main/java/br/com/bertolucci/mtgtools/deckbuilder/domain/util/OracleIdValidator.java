package br.com.bertolucci.mtgtools.deckbuilder.domain.util;

import java.util.UUID;

public class OracleIdValidator {

    public static UUID validate(String oracleId) {
        if (oracleId == null || oracleId.trim().isEmpty()) {
            return null;
        }

        if (UUIDValidator.isValid(oracleId.trim())) {
            return UUID.fromString(oracleId.trim());
        }

        throw new IllegalArgumentException("O ID Oracle deve ser inserido com o formato UUID");
    }

}
