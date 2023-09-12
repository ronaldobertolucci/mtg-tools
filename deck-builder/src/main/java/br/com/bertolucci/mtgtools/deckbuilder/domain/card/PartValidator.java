package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.util.EnumValidator;
import br.com.bertolucci.mtgtools.deckbuilder.domain.util.NotNullOrEmptyValidator;

public class PartValidator {

    public String validateName(String name) {
        if (NotNullOrEmptyValidator.isValid(name)) {
            return name.trim().toLowerCase();
        }

        throw new IllegalArgumentException("O nome da parte não pode ser nulo ou vazio");
    }

    public String validateTypeLine(String typeLine) {
        if (NotNullOrEmptyValidator.isValid(typeLine)) {
            return typeLine.trim();
        }

        throw new IllegalArgumentException("A linha do tipo da parte não pode ser nulo ou vazio");
    }

    public PartType validateType(String type) {
        if (EnumValidator.isValid(type, PartType.class)) {
            return PartType.valueOf(type.trim().toUpperCase());
        }

        throw new IllegalArgumentException("O tipo escolhido para a parte não existe");
    }

}
