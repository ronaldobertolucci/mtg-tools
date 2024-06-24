package br.com.bertolucci.mtgtools.deckbuilder.domain.set;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLInsert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sets", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "code")
@SQLInsert(sql = """
    INSERT INTO sets(code, image_uri, name, parent_set, released_at, total_cards, type, id)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (code) DO UPDATE
        SET
            image_uri=EXCLUDED.image_uri,
            name=EXCLUDED.name,
            released_at=EXCLUDED.released_at,
            total_cards=EXCLUDED.total_cards,
            type=EXCLUDED.type""")
@SequenceGenerator(name = "SEQUENCE_SET", sequenceName = "sets_id_seq", allocationSize = 1)
public class Set {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SET")
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SetType type;
    @Column(name = "released_at")
    private LocalDate releasedAt;
    @Column(name = "total_cards", nullable = false)
    private Integer totalCards;
    @OneToMany(mappedBy = "set", fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();
    @Column(name = "image_uri", nullable = false)
    private String imageUri;
    @Column(name = "parent_set")
    private String parentSet;

    @Override
    public String toString() {
        return this.name;
    }
}
