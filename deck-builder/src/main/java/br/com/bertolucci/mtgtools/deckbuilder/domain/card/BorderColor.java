package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

public enum BorderColor {
    BLACK("preto"),
    WHITE("branco"),
    BORDERLESS("sem borda"),
    SILVER("prata"),
    GOLD("dourado");

    private final String translatedName;

    BorderColor(String translatedName) {
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
