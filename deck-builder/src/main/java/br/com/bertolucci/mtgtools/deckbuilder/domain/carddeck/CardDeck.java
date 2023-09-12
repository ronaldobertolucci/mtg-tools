package br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import jakarta.persistence.*;

@Entity
@Table(name = "card_deck")
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

    public CardDeck() {
    }

    public CardDeck(Card card, Deck deck) {
        this.card = card;
        this.deck = deck;
    }

    public CardDeck(Card card, Deck deck, Integer quantity) {
        this.card = card;
        this.deck = deck;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDeck that = (CardDeck) o;
        return this.card.equals(that.card) && this.deck.equals(that.deck);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + card.hashCode();
        result = 31 * result + deck.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CardDeck{" + "card=" + card + ", deck=" + deck + ", quantity=" + quantity + '}';
    }
}
