package br.com.bertolucci.mtgtools.ui.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import org.apache.commons.text.WordUtils;

import java.util.List;

public class CardTableModel extends AbstractTableModel<Card> {

    public CardTableModel(List<Card> cards) {
        super(new String[]{"Nome", "Tipo", "Custo de Mana", "Raridade", "N° Coleção", "Ver"}, cards);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalize(list.get(rowIndex).getName());
            case 1:
                return WordUtils.capitalize(list.get(rowIndex).getTypeLine());
            case 2:
                return list.get(rowIndex).getManaCost();
            case 3:
                return WordUtils.capitalize(list.get(rowIndex).getCardRarity().getTranslatedName());
            case 4:
                return list.get(rowIndex).getCollectorNumber();
            case 5:
                return list.get(rowIndex);
            default:
                return "Dados não encontrados";
        }
    }
}