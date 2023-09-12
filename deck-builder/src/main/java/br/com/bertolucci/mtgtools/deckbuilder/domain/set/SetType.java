package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

public enum SetType {

    CORE("núcleo"),
    EXPANSION("expansão"),
    MASTERS("masters"),
    ALCHEMY("alchemy"),
    MASTERPIECE("masterpiece"),
    ARSENAL("arsenal"),
    FROM_THE_VAULT("from_the_vault"),
    SPELLBOOK("spellbook"),
    PREMIUM_DECK("premium_deck"),
    DUEL_DECK("duel_deck"),
    DRAFT_INNOVATION("draft"),
    TREASURE_CHEST("treasure_chest"),
    COMMANDER("commander"),
    PLANECHASE("planechase"),
    ARCHENEMY("archenemy"),
    VANGUARD("vanguard"),
    FUNNY("funny"),
    STARTER("starter"),
    BOX("box"),
    PROMO("promo"),
    TOKEN("token"),
    MEMORABILIA("memorabilia"),
    MINIGAME("minigame");

    private final String translatedName;

    SetType(String n) { translatedName = n; }

    public String getTranslatedName() {
        return translatedName;
    }

    @Override
    public String toString() {
        return translatedName;
    }
}
