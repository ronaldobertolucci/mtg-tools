package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

import br.com.bertolucci.mtgtools.deckbuilder.domain.util.EnumValidator;
import br.com.bertolucci.mtgtools.deckbuilder.domain.util.NotNullOrEmptyValidator;

import java.time.DateTimeException;
import java.time.LocalDate;

public class SetValidator {

    public String validateName(String name) {
        if (NotNullOrEmptyValidator.isValid(name)) {
            return name.trim().toLowerCase();
        }

        throw new IllegalArgumentException("O nome do set não pode ser nulo ou vazio");
    }

    public String validateCode(String code) {
        if (NotNullOrEmptyValidator.isValid(code)) {
            return code.trim().toLowerCase();
        }

        throw new IllegalArgumentException("O código do set não pode ser nulo ou vazio");
    }

    public SetType validateType(String type) {
        if (EnumValidator.isValid(type, SetType.class)) {
            return SetType.valueOf(type.trim().toUpperCase());
        }

        throw new IllegalArgumentException("O tipo do set não existe");
    }

    public LocalDate validateReleasedAt(String releasedAt) {
        try {
            return LocalDate.parse(releasedAt);
        } catch (Exception ignored) {
            return null;
        }
    }

    public String validateImageUri(String imageUri) {
        if (NotNullOrEmptyValidator.isValid(imageUri)) {
            return imageUri.trim();
        }

        throw new IllegalArgumentException("O caminho da imagem do set não pode ser nulo ou vazio");
    }

}
