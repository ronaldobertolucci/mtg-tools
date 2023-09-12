package br.com.bertolucci.mtgtools.deckbuilder.domain.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardDeck;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "decks")
public class Deck {

    @Transient
    private final DeckValidator validator = new DeckValidator();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.ORDINAL)
    private Format format;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
    private List<CardDeck> cards = new ArrayList<>();

    public Deck() {
    }

    public Deck(String format, String name) {
        this.format = validator.validateFormat(format);
        this.name = validator.validateName(name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    public Format getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = validator.validateFormat(format);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public List<CardDeck> getCards() {
        return cards;
    }

    public void addCard(CardDeck card) {
        this.cards.add(card);
        card.setDeck(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return this.name.equalsIgnoreCase(deck.getName());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + ": " + format;
    }
}
