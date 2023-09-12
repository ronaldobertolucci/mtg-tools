package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

public enum Lang {
    PT("português"),
    ES("espanhol"),
    EN("inglês"),
    FR("francês"),
    IT("italiano"),
    DE("alemão"),
    KO("coreano"),
    RU("russo"),
    JA("japonês"),
    PH("phyrexian"),
    HE("hebraico"),
    LA("latim"),
    GRC("grego antigo"),
    AR("arábico"),
    SA("sanscrito"),
    ZHS("chinês simplificado"),
    ZHT("chinês tradicional");

    private final String translatedName;

    Lang(String translatedName) {
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
