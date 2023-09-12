package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.CardDto;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Set set;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Lang lang;
    private String toughness;
    @Column(name = "type_line", nullable = false)
    private String typeLine;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "layout", nullable = false)
    private Layout layout;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "border_color", nullable = false)
    private BorderColor borderColor;
    @Column(name = "flavor_text", columnDefinition = "TEXT")
    private String flavorText;
    private String loyalty;
    @Enumerated(EnumType.ORDINAL)
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
    @Enumerated(EnumType.ORDINAL)
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

    public Card() {
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    @Enumerated(EnumType.STRING)
    public Lang getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = validator.validateLang(lang);
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getTypeLine() {
        return typeLine;
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = validator.validateTypeLine(typeLine);
    }

    @Enumerated(EnumType.STRING)
    public Layout getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = validator.validateLayout(layout);
    }

    @Enumerated(EnumType.STRING)
    public BorderColor getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = validator.validateBorderColor(borderColor);
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(String loyalty) {
        this.loyalty = loyalty;
    }

    @Enumerated(EnumType.STRING)
    public ImageStatus getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = validator.validateImageStatus(imageStatus);
    }

    public String getCollectorNumber() {
        return collectorNumber;
    }

    public void setCollectorNumber(String collectorNumber) {
        this.collectorNumber = validator.validateCollectorNumber(collectorNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public Boolean isDigital() {
        return isDigital;
    }

    public void setDigital(Boolean digital) {
        isDigital = digital;
    }

    public String getOracleText() {
        return oracleText;
    }

    public void setOracleText(String oracleText) {
        this.oracleText = validator.validateOracleText(oracleText);
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public Double getCmc() {
        return cmc;
    }

    public void setCmc(Double cmc) {
        this.cmc = validator.validateCmc(cmc);
    }

    @Enumerated(EnumType.STRING)
    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = validator.validateRarity(rarity);
    }

    public UUID getOracleId() {
        return oracleId;
    }

    public void setOracleId(String oracleId) {
        this.oracleId = validator.validateOracleId(oracleId);
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getRulingsUri() {
        return rulingsUri;
    }

    public void setRulingsUri(String rulingsUri) {
        this.rulingsUri = rulingsUri;
    }

    public PrintedFields getPrintedFields() {
        return printedFields;
    }

    public void setPrintedFields(PrintedFields printedFields) {
        this.printedFields = printedFields;
    }

    public Legalities getLegalities() {
        return legalities;
    }

    public void setLegalities(Legalities legalities) {
        this.legalities = legalities;
    }

    public List<Part> getParts() {
        return this.parts;
    }

    public void addPart(Part part) {
        this.parts.add(part);
        part.setCard(this);
    }

    public List<Ruling> getRulings() {
        return rulings;
    }

    public void addRuling(Ruling ruling) {
        this.rulings.add(ruling);
        ruling.setCard(this);
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void addFace(Face face) {
        this.faces.add(face);
        face.setCard(this);
    }

    public List<CardDeck> getCardDeckList() {
        return cardDeckList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return this.set.equals(card.set) && this.name.equalsIgnoreCase(card.getName())
                && this.collectorNumber.equalsIgnoreCase(card.getCollectorNumber());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + set.hashCode();
        result = 31 * result + collectorNumber.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
