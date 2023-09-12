package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

public enum Layout {
    NORMAL("normal"),
    SPLIT("dividido (split)"),
    FLIP("virar (flip)"),
    TRANSFORM("transformar (transform)"),
    MODAL_DFC("token de duas faces modal (modal DFC)"),
    MELD("fundir (meld)"),
    LEVELER("nivelador (leveler)"),
    CLASS("classe (class)"),
    SAGA("saga"),
    ADVENTURE("aventura (adventure)"),
    MUTATE("mutação (mutate)"),
    PROTOTYPE("protótipo (prototype)"),
    BATTLE("batalha (battle)"),
    PLANAR("planar"),
    SCHEME("esquema (scheme)"),
    // VANGUARD("vanguarda (vanguard)"),
    TOKEN("token"),
    DOUBLE_FACED_TOKEN("token de duas faces"),
    EMBLEM("emblema (emblem)"),
    AUGMENT("aumentar (augment)"),
    HOST("host"),
    ART_SERIES("art_series");

    private final String translatedName;

    Layout(String translatedName) {
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
