package br.com.bertolucci.mtgtools.deckbuilder.domain.symbol;

import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "symbols")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "symbol")
public class Symbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Transient
    private final SymbolValidator validator = new SymbolValidator();

    public Symbol(String symbol, String description, Boolean representsMana, Double manaValue,
                  String imageUri) {
        this.symbol = validator.validateSymbol(symbol);
        this.description = validator.validateDescription(description);
        this.representsMana = validator.validateRepresentsMana(representsMana);
        this.manaValue = manaValue;
        this.imageUri = imageUri;
    }

    public Symbol(SymbolDto symbolDto) {
        this.symbol = validator.validateSymbol(symbolDto.symbol());
        this.description = validator.validateDescription(symbolDto.description());
        this.representsMana = validator.validateRepresentsMana(symbolDto.representsMana());
        this.manaValue = symbolDto.manaValue();
        this.imageUri = symbolDto.imageUri();
    }

    public void setSymbol(String symbol) {
        this.symbol = validator.validateSymbol(symbol);
    }

    public void setDescription(String description) {
        this.description = validator.validateDescription(description);
    }

    public void setRepresentsMana(Boolean representsMana) {
        this.representsMana = validator.validateRepresentsMana(representsMana);
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
