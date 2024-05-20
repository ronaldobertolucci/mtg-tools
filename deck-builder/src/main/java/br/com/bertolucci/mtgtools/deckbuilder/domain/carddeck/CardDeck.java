package br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "card_deck")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"card", "deck"})
@ToString(of = {"card", "deck", "quantity"})
public class CardDeck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
    @ManyToOne(fetch = FetchType.LAZY)
    private Deck deck;
    @Column(nullable = false)
    private Integer quantity;

    public CardDeck(Card card, Deck deck) {
        this.card = card;
        this.deck = deck;
    }

    public CardDeck(Card card, Deck deck, Integer quantity) {
        this.card = card;
        this.deck = deck;
        this.quantity = quantity;
    }
}
