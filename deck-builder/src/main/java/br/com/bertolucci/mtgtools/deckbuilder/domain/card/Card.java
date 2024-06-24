package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(
        name = "cards",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "set_id", "name", "collector_number" })},
        indexes = {
                @Index(columnList = "name", name = "card_name_idx"),
                @Index(columnList = "mana_cost", name = "card_mana_cost_idx"),
                @Index(columnList = "type_line", name = "card_type_line_idx"),
                @Index(columnList = "power", name = "card_power_idx"),
                @Index(columnList = "toughness", name = "card_toughness_idx"),
                @Index(columnList = "loyalty", name = "card_loyalty_idx"),
                @Index(columnList = "oracle_text", name = "card_oracle_text_idx")
        }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"set", "name", "collectorNumber"})
@SequenceGenerator(name = "SEQUENCE_CARD", sequenceName = "cards_id_seq", allocationSize = 1)
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_CARD")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Set set;
    @Column(name = "type_line", nullable = false)
    private String typeLine;
    @Column(nullable = false)
    private String name;
    @Column(name = "collector_number", nullable = false)
    private String collectorNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "image_status", nullable = false)
    private CardImageStatus imageStatus;
    private String toughness;
    private String power;
    private String loyalty;
    @Column(name = "is_digital")
    private Boolean isDigital;
    @Column(name = "oracle_text", columnDefinition = "TEXT")
    private String oracleText;
    @Column(name = "mana_cost")
    private String manaCost;
    private Double cmc;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "rarity")
    private CardRarity cardRarity;
    @Column(name = "image_uri")
    private String imageUri;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.Set<Face> faces = new HashSet<>();
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.Set<CardLegality> legalities = new HashSet<>();
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CardDeck> cardDecks = new ArrayList<>();

    public void addFace(Face face) {
        this.faces.add(face);
        face.setCard(this);
    }

    public void addCardLegality(CardLegality cardLegality) {
        this.legalities.add(cardLegality);
        cardLegality.setCard(this);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
