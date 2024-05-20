package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import br.com.bertolucci.mtgtools.shared.card.FaceDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "faces")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "card"})
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

    public void setName(String name) {
        this.name = validator.validateName(name);
    }

    public void setOracleId(String oracleId) {
        this.oracleId = validator.validateOracleId(oracleId);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
