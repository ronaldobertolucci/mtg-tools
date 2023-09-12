package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.util.EnumValidator;
import br.com.bertolucci.mtgtools.deckbuilder.domain.util.NotNullOrEmptyValidator;
import br.com.bertolucci.mtgtools.deckbuilder.domain.util.OracleIdValidator;

import java.util.UUID;

public class CardValidator {

    public Lang validateLang(String lang) {
        if (EnumValidator.isValid(lang, Lang.class)) {
            return Lang.valueOf(lang.trim().toUpperCase());
        }

        throw new IllegalArgumentException("A língua escolhida para o card não existe");
    }

    public String validateTypeLine(String typeLine) {
        if (NotNullOrEmptyValidator.isValid(typeLine)) {
            return typeLine.trim();
        }

        throw new IllegalArgumentException("A linha de tipo do card não pode ser nula ou vazia");
    }

    public Layout validateLayout(String layout) {
        if (EnumValidator.isValid(layout, Layout.class)) {
            return Layout.valueOf(layout.trim().toUpperCase());
        }

        throw new IllegalArgumentException("O layout escolhido para o card não existe");
    }

    public BorderColor validateBorderColor(String borderColor) {
        if (EnumValidator.isValid(borderColor, BorderColor.class)) {
            return BorderColor.valueOf(borderColor.trim().toUpperCase());
        }

        throw new IllegalArgumentException("A cor de borda escolhida para o card não existe");
    }

    public ImageStatus validateImageStatus(String imageStatus) {
        if (EnumValidator.isValid(imageStatus, ImageStatus.class)) {
            return ImageStatus.valueOf(imageStatus.trim().toUpperCase());
        }

        throw new IllegalArgumentException("A situação da imagem escolhida para o card não existe");
    }

    public String validateCollectorNumber(String collectorNumber) {
        if (NotNullOrEmptyValidator.isValid(collectorNumber)) {
            return collectorNumber.trim();
        }

        throw new IllegalArgumentException("O número de coleção do card não pode ser nulo ou vazio");
    }

    public String validateName(String name) {
        if (NotNullOrEmptyValidator.isValid(name)) {
            return name.trim().toLowerCase();
        }

        throw new IllegalArgumentException("O nome do card não pode ser nulo ou vazio");
    }

    public Rarity validateRarity(String rarity) {
        if (EnumValidator.isValid(rarity, Rarity.class)) {
            return Rarity.valueOf(rarity.trim().toUpperCase());
        }

        throw new IllegalArgumentException("A raridade escolhida para o card não existe");
    }

    public String validateOracleText(String oracleText) {
        if (oracleText != null) {
            return oracleText.trim();
        }

        return null;
    }

    public Double validateCmc(Double cmc) {
        if (cmc != null && cmc >= 0.0) {
            return cmc;
        }

        throw new IllegalArgumentException("A custo convertido de mana deve ser maior ou igual a zero");
    }

    public UUID validateOracleId(String oracleId) {
        return OracleIdValidator.validate(oracleId);
    }
}
