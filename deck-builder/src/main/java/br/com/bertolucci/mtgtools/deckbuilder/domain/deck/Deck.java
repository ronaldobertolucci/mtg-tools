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
@Table(name = "decks", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@SequenceGenerator(name = "SEQUENCE_DECK", sequenceName = "decks_id_seq", allocationSize = 1)
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_DECK")
    private Integer id;
    @Enumerated(EnumType.STRING)
    private DeckFormat deckFormat;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CardDeck> cards = new ArrayList<>();

    public void addCard(CardDeck card) {
        this.cards.add(card);
        card.setDeck(this);
    }

    @Override
    public String toString() {
        return name + ": " + deckFormat;
    }
}
