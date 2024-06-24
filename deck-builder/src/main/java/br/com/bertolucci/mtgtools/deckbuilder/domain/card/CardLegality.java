package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(
        name = "card_legalities",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "card_id", "deck_format" }) },
        indexes = {
                @Index(columnList = "deck_format", name = "deck_format_idx"),
                @Index(columnList = "card_id", name = "card_legality_card_id_idx")
        }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"card", "legality", "deckFormat"})
@ToString(of = {"card", "legality", "deckFormat"})
@SequenceGenerator(name = "SEQUENCE_CARD_LEGALITIES", sequenceName = "card_legalities_id_seq", allocationSize = 1)
public class CardLegality implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_CARD_LEGALITIES")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Legality legality;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "deck_format")
    private DeckFormat deckFormat;

    public CardLegality(Card card, Legality legality, DeckFormat deckFormat) {
        this.card = card;
        this.legality = legality;
        this.deckFormat = deckFormat;
    }
}
