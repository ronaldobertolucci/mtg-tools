package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import org.apache.commons.text.WordUtils;

import java.util.List;

public class DeckBuilderCardTableModel extends AbstractTableModel<Card> {
    public DeckBuilderCardTableModel(List<Card> cards) {
        super(new String[]{"Nome", "Set", "Tipo", "Custo de Mana", "Poder", "Resistência", "Lealdade", "Texto",
                "Ver", "Adicionar"}, cards);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalize(list.get(rowIndex).getName());
            case 1:
                return WordUtils.capitalize(list.get(rowIndex).getSet().getName());
            case 2:
                return WordUtils.capitalize(list.get(rowIndex).getTypeLine());
            case 3:
                return list.get(rowIndex).getManaCost();
            case 4:
                return list.get(rowIndex).getPower();
            case 5:
                return list.get(rowIndex).getToughness();
            case 6:
                return list.get(rowIndex).getLoyalty();
            case 7:
            case 8:
            case 9:
                return list.get(rowIndex);
            default:
                return "Dados não encontrados";
        }
    }
}
