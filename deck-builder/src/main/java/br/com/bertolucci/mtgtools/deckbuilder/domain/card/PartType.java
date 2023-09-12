package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

public enum PartType {

    TOKEN("token"),
    MELD_PART("parte de fusão"),
    MELD_RESULT("resultado de fusão"),
    COMBO_PIECE("peça de combo");

    private final String translatedName;

    PartType(String translatedName) {
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
