package br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "card_deck", uniqueConstraints = { @UniqueConstraint(columnNames = { "card", "deck", "is_sideboard" }) })
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"card", "deck", "isSideboard"})
@ToString(of = {"card", "deck", "quantity", "isSideboard"})
@SequenceGenerator(name = "SEQUENCE_CARD_DECK", sequenceName = "card_deck_id_seq", allocationSize = 1)
public class CardDeck {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_CARD_DECK")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Card card;
    @ManyToOne(fetch = FetchType.EAGER)
    private Deck deck;
    @Column(nullable = false)
    private Integer quantity;
    @Column(name = "is_sideboard", nullable = false)
    private Boolean isSideboard;

    public CardDeck(Card card, Deck deck, Integer quantity, Boolean isSideboard) {
        this.card = card;
        this.deck = deck;
        this.quantity = quantity;
        this.isSideboard = isSideboard;
    }

    public CardDeck(Card card, Deck deck) {
        this.card = card;
        this.deck = deck;
    }
}
