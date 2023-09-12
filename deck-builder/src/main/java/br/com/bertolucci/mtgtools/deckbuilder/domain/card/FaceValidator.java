package br.com.bertolucci.mtgtools.deckbuilder.domain.card;


import br.com.bertolucci.mtgtools.deckbuilder.domain.util.NotNullOrEmptyValidator;
import br.com.bertolucci.mtgtools.deckbuilder.domain.util.OracleIdValidator;

import java.util.UUID;

public class FaceValidator {

    public String validateName(String name) {
        if (NotNullOrEmptyValidator.isValid(name)) {
            return name.trim().toLowerCase();
        }

        throw new IllegalArgumentException("O nome da face não pode ser nulo ou vazio");
    }

    public UUID validateOracleId(String oracleId) {
        return OracleIdValidator.validate(oracleId);
    }
}
