package br.com.bertolucci.mtgtools.deckbuilder.domain.symbol;

import br.com.bertolucci.mtgtools.shared.symbol.SymbolDto;
import org.apache.commons.lang3.Validate;

public class SymbolBuilder {

    private Symbol symbol = new Symbol();

    public SymbolBuilder(SymbolDto symbolDto) {
        this.setSymbol(symbolDto.symbol());
        this.setDescription(symbolDto.description());
        this.setRepresentsMana(symbolDto.representsMana());
        this.setManaValue(symbolDto.manaValue());
        this.setImageUri(symbolDto.imageUri());
    }

    public SymbolBuilder setSymbol(String symbol) {
        this.symbol.setSymbol(Validate.notBlank(symbol, "O símbolo não pode estar em branco"));
        return this;
    }

    public SymbolBuilder setDescription(String description) {
        this.symbol.setDescription(Validate.notBlank(description,
                "A descrição do símbolo não pode estar em branco"));
        return this;
    }

    public SymbolBuilder setRepresentsMana(Boolean representsMana) {
        this.symbol.setRepresentsMana(Validate.notNull(representsMana,
                "A representação de mana do símbolo não pode ser nula"));
        return this;
    }

    public SymbolBuilder setManaValue(Double manaValue) {
        this.symbol.setManaValue(manaValue);
        return this;
    }

    public SymbolBuilder setImageUri(String imageUri) {
        this.symbol.setImageUri(imageUri);
        return this;
    }

    public Symbol build() {
        return this.symbol;
    }

}
