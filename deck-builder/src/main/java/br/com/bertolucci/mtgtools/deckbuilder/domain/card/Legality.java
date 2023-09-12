package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

public enum Legality {
    LEGAL("legal"),
    NOT_LEGAL("ilegal"),
    RESTRICTED("restrito"),
    BANNED("banido");

    private final String translatedName;

    Legality(String translatedName) {
        this.translatedName = translatedName;
    }

    public String getTranslatedName() {
        return translatedName;
    }

    @Override
    public String toString() {
        return translatedName;
    }
}
