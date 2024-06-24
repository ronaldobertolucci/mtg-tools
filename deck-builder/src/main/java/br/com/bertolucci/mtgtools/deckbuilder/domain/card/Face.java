package br.com.bertolucci.mtgtools.deckbuilder.domain.card;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(
        name = "faces",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "card" })},
        indexes = {
            @Index(columnList = "card_id", name = "face_card_id_idx"),
            @Index(columnList = "name", name = "face_name_idx"),
            @Index(columnList = "mana_cost", name = "face_mana_cost_idx"),
            @Index(columnList = "type_line", name = "face_type_line_idx"),
            @Index(columnList = "power", name = "face_power_idx"),
            @Index(columnList = "toughness", name = "face_toughness_idx"),
            @Index(columnList = "loyalty", name = "face_loyalty_idx"),
            @Index(columnList = "oracle_text", name = "face_oracle_text_idx")
        }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "card"})
@SequenceGenerator(name = "SEQUENCE_FACE", sequenceName = "faces_id_seq", allocationSize = 1)
public class Face implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_FACE")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;
    private String toughness;
    @Column(name = "type_line")
    private String typeLine;
    private String loyalty;
    @Column(nullable = false)
    private String name;
    @Column(name = "oracle_text", columnDefinition = "TEXT")
    private String oracleText;
    private String power;
    @Column(name = "mana_cost")
    private String manaCost;
    @Column(name = "image_uri")
    private String imageUri;

    @Override
    public String toString() {
        return this.name;
    }
}
