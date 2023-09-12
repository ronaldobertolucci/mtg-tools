package br.com.bertolucci.mtgtools.ui.part;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Part;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import org.apache.commons.text.WordUtils;

import java.util.List;

public class PartTableModel extends AbstractTableModel<Part> {

    public PartTableModel(List<Part> parts) {
        super(new String[]{"Nome", "Tipo", "Tipo da parte", "Editar", "Excluir"}, parts);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalize(list.get(rowIndex).getName());
            case 1:
                return list.get(rowIndex).getTypeLine();
            case 2:
                return list.get(rowIndex).getType().getTranslatedName();
            case 3:
            case 4:
                return list.get(rowIndex);
            default:
                return "Dados não encontrados";
        }
    }
}