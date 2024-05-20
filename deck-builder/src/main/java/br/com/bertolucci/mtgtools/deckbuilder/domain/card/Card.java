package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"set", "name", "collectorNumber"})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Set set;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Lang lang;
    private String toughness;
    @Column(name = "type_line", nullable = false)
    private String typeLine;
    @Enumerated(EnumType.STRING)
    @Column(name = "layout", nullable = false)
    private Layout layout;
    @Enumerated(EnumType.STRING)
    @Column(name = "border_color", nullable = false)
    private BorderColor borderColor;
    @Column(name = "flavor_text", columnDefinition = "TEXT")
    private String flavorText;
    private String loyalty;
    @Enumerated(EnumType.STRING)
    @Column(name = "image_status", nullable = false)
    private ImageStatus imageStatus;
    @Column(name = "collector_number", nullable = false)
    private String collectorNumber;
    @Column(nullable = false)
    private String name;
    @Column(name = "is_digital", nullable = false)
    private Boolean isDigital;
    @Column(name = "oracle_text", columnDefinition = "TEXT")
    private String oracleText;
    private String power;
    @Column(name = "mana_cost")
    private String manaCost;
    private Double cmc;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rarity rarity;
    @Column(name = "oracle_id")
    private UUID oracleId;
    @Column(name = "image_uri")
    private String imageUri;
    @Column(name = "rulings_uri")
    private String rulingsUri;
    @Embedded
    private PrintedFields printedFields;
    @Embedded
    private Legalities legalities = new Legalities();
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Part> parts = new ArrayList<>();
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Face> faces = new ArrayList<>();
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ruling> rulings = new ArrayList<>();
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CardDeck> cardDeckList = new ArrayList<>();
    @Transient
    private final CardValidator validator = new CardValidator();

    public Card(Set set, String lang, String typeLine, String layout, String borderColor, String imageStatus,
                String collectorNumber, String name, Double cmc, Boolean isDigital, String rarity, String imageUri) {
        this.set = set;
        this.lang = validator.validateLang(lang);
        this.typeLine = validator.validateTypeLine(typeLine);
        this.layout = validator.validateLayout(layout);
        this.borderColor = validator.validateBorderColor(borderColor);
        this.imageStatus = validator.validateImageStatus(imageStatus);
        this.collectorNumber = validator.validateCollectorNumber(collectorNumber);
        this.name = validator.validateName(name);
        this.cmc = validator.validateCmc(cmc);
        this.isDigital = isDigital;
        this.rarity = validator.validateRarity(rarity);
        this.imageUri = imageUri;
    }

    public Card(CardDto cardDto) {
        this.lang = validator.validateLang(cardDto.lang());
        this.typeLine = validator.validateTypeLine(cardDto.typeLine());
        this.layout = validator.validateLayout(cardDto.layoutType());
        this.borderColor = validator.validateBorderColor(cardDto.borderColor());
        this.imageStatus = validator.validateImageStatus(cardDto.imageStatus());
        this.collectorNumber = validator.validateCollectorNumber(cardDto.collectorNumber());
        this.name = validator.validateName(cardDto.name());
        this.isDigital = cardDto.isDigital();
        this.rarity = validator.validateRarity(cardDto.rarity());
        this.oracleId = validator.validateOracleId(cardDto.oracleId());
        this.flavorText = cardDto.flavorText();
        this.toughness = cardDto.toughness();
        this.power = cardDto.power();
        this.loyalty = cardDto.loyalty();
        this.oracleText = validator.validateOracleText(cardDto.oracleText());
        this.manaCost = cardDto.manaCost();
        this.cmc = validator.validateCmc(cardDto.cmc());
        this.imageUri = cardDto.imageUris() != null ? cardDto.imageUris().get("png") : null;
        this.rulingsUri = cardDto.rulingsUri() != null ? cardDto.rulingsUri() : null;

    }

    public void setLang(String lang) {
        this.lang = validator.validateLang(lang);
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = validator.validateTypeLine(typeLine);
    }

    public void setLayout(String layout) {
        this.layout = validator.validateLayout(layout);
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = validator.validateBorderColor(borderColor);
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = validator.validateImageStatus(imageStatus);
    }

    public void setCollectorNumber(String collectorNumber) {
        this.collectorNumber = validator.validateCollectorNumber(collectorNumber);
    }

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public void setOracleText(String oracleText) {
        this.oracleText = validator.validateOracleText(oracleText);
    }

    public void setCmc(Double cmc) {
        this.cmc = validator.validateCmc(cmc);
    }

    public void setRarity(String rarity) {
        this.rarity = validator.validateRarity(rarity);
    }

    public void setOracleId(String oracleId) {
        this.oracleId = validator.validateOracleId(oracleId);
    }

    public void addPart(Part part) {
        this.parts.add(part);
        part.setCard(this);
    }

    public void addRuling(Ruling ruling) {
        this.rulings.add(ruling);
        ruling.setCard(this);
    }

    public void addFace(Face face) {
        this.faces.add(face);
        face.setCard(this);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
