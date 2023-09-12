package br.com.bertolucci.mtgtools.ui.set;

import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import org.apache.commons.text.WordUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class SetTableModel extends AbstractTableModel<Set> {

    public SetTableModel(List<Set> sets) {
        super(new String[]{"Nome", "Código", "Lançamento", "Tipo", "Cards"}, sets);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalize(list.get(rowIndex).getName());
            case 1:
                return list.get(rowIndex).getCode().toUpperCase();
            case 2:
                return list.get(rowIndex).getReleasedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            case 3:
                return list.get(rowIndex).getType().getTranslatedName();
            case 4:
                return list.get(rowIndex).getTotalCards();
            default:
                return "Dados não encontrados";
        }
    }
}