package br.com.bertolucci.mtgtools.deckbuilder.domain.deck;

public enum DeckFormat {

    STANDARD("padrão (standard)"),
    //ALCHEMY("alchemy"),
    //BRAWL("briga (brawl)"),
    //HISTORIC_BRAWL("briga histórica (historic brawl)"),
    COMMANDER("comandante (commander)"),
    //PAUPER_COMMANDER("comandante pedinte (pauper commander)"),
    //DUEL("duelo"),
    //EXPLORER("explorador (explorer)"),
    //FUTURE("futuro (future)"),
    HISTORIC("histórico (historic)"),
    //GLADIATOR("gladiador (gladiator)"),
    LEGACY("legado (legacy)"),
    MODERN("moderno (modern)"),
    //PENNY("moeda (penny)"),
    //OLDSCHOOL("oldschool"),
    PIONEER("pioneiro (pioneer)");
    //PAUPER("pedinte (pauper)"),
    //OATHBREAKER("perjuro (oathbreaker)"),
    //VINTAGE("vintage"),
    //PRE_MODERN("pré-moderno");

    private String translatedName;

    DeckFormat(String translatedName) {
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
