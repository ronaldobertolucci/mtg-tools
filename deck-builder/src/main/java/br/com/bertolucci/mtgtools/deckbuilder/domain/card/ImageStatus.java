package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

public enum ImageStatus {
    MISSING("sem imagem"),
    PLACEHOLDER("imagem genérica"),
    LOWRES("baixa resolução"),
    HIGHRES_SCAN("alta resolução");

    private final String translatedName;

    ImageStatus(String translatedName) {
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
