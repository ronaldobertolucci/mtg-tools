package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import org.apache.commons.text.WordUtils;

import java.util.List;

public class DeckTableModel extends AbstractTableModel<Deck> {

    public DeckTableModel(List<Deck> decks) {
        super(new String[]{"Nome", "Formato", "Construtor", "Editar", "Excluir"}, decks);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalize(list.get(rowIndex).getName());
            case 1:
                return WordUtils.capitalize(list.get(rowIndex).getDeckFormat().getTranslatedName());
            case 2:
            case 3:
            case 4:
                return list.get(rowIndex);
            default:
                return "Dados não encontrados";
        }
    }
}