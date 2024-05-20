package br.com.bertolucci.mtgtools.deckbuilder.domain.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "decks")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class Deck {

    @Transient
    private final DeckValidator validator = new DeckValidator();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Format format;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL)
    private List<CardDeck> cards = new ArrayList<>();

    public Deck(String format, String name) {
        this.format = validator.validateFormat(format);
        this.name = validator.validateName(name);
    }

    public void setFormat(String format) {
        this.format = validator.validateFormat(format);
    }

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public void addCard(CardDeck card) {
        this.cards.add(card);
        card.setDeck(this);
    }

    @Override
    public String toString() {
        return name + ": " + format;
    }
}
