package br.com.bertolucci.mtgtools.deckbuilder.domain.deck;

import org.apache.commons.lang3.Validate;

public class DeckBuilder {

    private Deck deck = new Deck();

    public DeckBuilder(String name, String format) {
        this.setName(name);
        this.setFormat(DeckFormat.valueOf(format.toUpperCase()));
    }

    public DeckBuilder setName(String name) {
        this.deck.setName(Validate.notBlank(name, "O nome do deck não pode estar em branco"));
        return this;
    }

    public DeckBuilder setFormat(DeckFormat deckFormat) {
        this.deck.setDeckFormat(deckFormat);
        return this;
    }

    public Deck build() {
        return this.deck;
    }
}
