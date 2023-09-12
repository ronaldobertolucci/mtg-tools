package br.com.bertolucci.mtgtools.deckbuilder.domain.symbol;

import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;
import jakarta.persistence.*;

@Entity
@Table(name = "symbols")
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

    public Symbol() {
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = validator.validateSymbol(symbol);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = validator.validateDescription(description);
    }

    public Boolean getRepresentsMana() {
        return representsMana;
    }

    public void setRepresentsMana(Boolean representsMana) {
        this.representsMana = validator.validateRepresentsMana(representsMana);
    }

    public Double getManaValue() {
        return manaValue;
    }

    public void setManaValue(Double manaValue) {
        this.manaValue = manaValue;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return this.symbol.equalsIgnoreCase(symbol.symbol);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + symbol.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
