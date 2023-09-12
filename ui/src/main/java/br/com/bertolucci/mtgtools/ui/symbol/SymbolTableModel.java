package br.com.bertolucci.mtgtools.ui.symbol;

import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;

import java.util.List;

public class SymbolTableModel extends AbstractTableModel<Symbol> {
    public SymbolTableModel(List<Symbol> symbols) {
        super(new String[]{"Símbolo", "Código", "Representa mana", "Valor de mana", "Descrição (em inglês)"}, symbols);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0, 1:
                return list.get(rowIndex).getSymbol();
            case 2:
                return list.get(rowIndex).getRepresentsMana() ? "Sim" : "Não";
            case 3:
                return list.get(rowIndex).getManaValue();
            case 4:
                return list.get(rowIndex).getDescription();
            default:
                return "Dados não encontrados";
        }
    }
}