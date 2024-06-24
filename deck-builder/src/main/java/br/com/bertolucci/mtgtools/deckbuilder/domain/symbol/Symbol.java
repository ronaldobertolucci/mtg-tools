package br.com.bertolucci.mtgtools.deckbuilder.domain.symbol;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLInsert;

@Entity
@Table(name = "symbols", uniqueConstraints = { @UniqueConstraint(columnNames = { "symbol" }) })
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "symbol")
@SQLInsert(sql = """
    INSERT INTO symbols(description, image_uri, mana_value, represents_mana, symbol, id)
        VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (symbol) DO UPDATE
        SET
            description=EXCLUDED.description,
            image_uri=EXCLUDED.image_uri,
            mana_value=EXCLUDED.mana_value,
            represents_mana=EXCLUDED.represents_mana""")
@SequenceGenerator(name = "SEQUENCE_SYMBOL", sequenceName = "symbols_id_seq", allocationSize = 1)
public class Symbol {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SYMBOL")
    private Integer id;
    @Column(unique = true, nullable = false)
    private String symbol;
    @Column(nullable = false)
    private String description;
    @Column(name = "represents_mana", nullable = false)
    private Boolean representsMana;
    @Column(name = "mana_value")
    private Double manaValue;
    @Column(name = "image_uri")
    private String imageUri;

    @Override
    public String toString() {
        return this.symbol;
    }
}
