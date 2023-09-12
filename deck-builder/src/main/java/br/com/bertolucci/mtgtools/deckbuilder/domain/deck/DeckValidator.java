package br.com.bertolucci.mtgtools.deckbuilder.domain.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.util.EnumValidator;
import br.com.bertolucci.mtgtools.deckbuilder.domain.util.NotNullOrEmptyValidator;

public class DeckValidator {

    public String validateName(String name) {
        if (NotNullOrEmptyValidator.isValid(name)) {
            return name.trim().toLowerCase();
        }

        throw new IllegalArgumentException("O nome do deck não pode ser nulo ou vazio");
    }

    public Format validateFormat(String format) {
        if (EnumValidator.isValid(format, Format.class)) {
            return Format.valueOf(format.trim().toUpperCase());
        }

        throw new IllegalArgumentException("O formato escolhido para o deck não existe");
    }

}
