package br.com.bertolucci.mtgtools.deckbuilder.domain.symbol;

import br.com.bertolucci.mtgtools.deckbuilder.domain.util.NotNullOrEmptyValidator;

public class SymbolValidator {

    public String validateSymbol(String symbol) {
        if (NotNullOrEmptyValidator.isValid(symbol)) {
            return symbol.trim().toUpperCase();
        }

        throw new IllegalArgumentException("O símbolo não pode ser nulo ou vazio");
    }

    public String validateDescription(String description) {
        if (NotNullOrEmptyValidator.isValid(description)) {
            return description.trim().toLowerCase();
        }

        throw new IllegalArgumentException("A descrição do símbolo não pode ser nula ou vazia");
    }

    public Boolean validateRepresentsMana(Boolean representsMana) {
        if (representsMana != null) {
            return representsMana;
        }

        throw new IllegalArgumentException("O 'símbolo representa mana' não pode ser nulo ou vazio");
    }

}
