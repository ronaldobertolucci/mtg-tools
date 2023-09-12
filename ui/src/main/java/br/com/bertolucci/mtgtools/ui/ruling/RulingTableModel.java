package br.com.bertolucci.mtgtools.ui.ruling;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Ruling;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import org.apache.commons.text.WordUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RulingTableModel extends AbstractTableModel<Ruling> {

    public RulingTableModel(List<Ruling> rulings) {
        super(new String[]{"Fonte", "Publicação", "Comentário", "Editar", "Excluir"}, rulings);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalize(list.get(rowIndex).getSource());
            case 1:
                return list.get(rowIndex).getPublishedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            case 2:
                return list.get(rowIndex).getComment();
            case 3:
            case 4:
                return list.get(rowIndex);
            default:
                return "Dados não encontrados";
        }
    }
}