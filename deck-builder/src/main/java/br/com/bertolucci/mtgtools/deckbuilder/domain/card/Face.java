package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.FaceDto;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "faces")
public class Face {

    @Transient
    private final FaceValidator validator = new FaceValidator();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
    private String toughness;
    @Column(name = "type_line")
    private String typeLine;
    @Column(name = "flavor_text", columnDefinition = "TEXT")
    private String flavorText;
    private String loyalty;
    @Column(nullable = false)
    private String name;
    @Column(name = "oracle_text", columnDefinition = "TEXT")
    private String oracleText;
    private String power;
    @Column(name = "mana_cost")
    private String manaCost;
    @Column(name = "oracle_id")
    private UUID oracleId;
    @Column(name = "image_uri")
    private String imageUri;

    public Face() {
    }

    public Face(Card card, String name) {
        this.card = card;
        this.name = validator.validateName(name);
    }

    public Face(FaceDto faceDto) {
        this.flavorText = faceDto.flavorText();
        this.name = validator.validateName(faceDto.name());
        this.toughness = faceDto.toughness();
        this.power = faceDto.power();
        this.typeLine = faceDto.typeLine();
        this.loyalty = faceDto.loyalty();
        this.manaCost = faceDto.manaCost();
        this.oracleText = faceDto.oracleText();
        this.oracleId = validator.validateOracleId(faceDto.oracleId());
        this.imageUri = faceDto.imageUris() != null ? faceDto.imageUris().get("png") : null;
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
        this.typeLine = typeLine;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public String getOracleText() {
        return oracleText;
    }

    public void setOracleText(String oracleText) {
        this.oracleText = oracleText;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
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

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Face face = (Face) o;
        return this.name.equalsIgnoreCase(face.getName()) && this.card.equals(face.card);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + card.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
