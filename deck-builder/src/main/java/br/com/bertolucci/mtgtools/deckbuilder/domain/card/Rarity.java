package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

public enum Rarity {
    COMMON("comum"),
    UNCOMMON("incomum"),
    RARE("raro"),
    SPECIAL("especial"),
    MYTHIC("mítico"),
    BONUS("bônus");

    private final String translatedName;

    Rarity(String translatedName) {
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
