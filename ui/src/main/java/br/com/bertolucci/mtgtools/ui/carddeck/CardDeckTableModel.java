package br.com.bertolucci.mtgtools.ui.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import org.apache.commons.text.WordUtils;

import java.util.List;

public class CardDeckTableModel extends AbstractTableModel<CardDeck> {

    public CardDeckTableModel(List<CardDeck> cardDecks) {
        super(new String[]{"Card", "Qtd", "Excluir"}, cardDecks);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalize(list.get(rowIndex).getCard().getName());
            case 1:
                return list.get(rowIndex).getQuantity();
            case 2:
                return list.get(rowIndex);
            default:
                return "Dados não encontrados";
        }
    }
}
