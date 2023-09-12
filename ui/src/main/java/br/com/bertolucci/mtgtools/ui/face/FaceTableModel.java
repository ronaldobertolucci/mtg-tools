package br.com.bertolucci.mtgtools.ui.face;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import org.apache.commons.text.WordUtils;

import java.util.List;

public class FaceTableModel extends AbstractTableModel<Face> {

    public FaceTableModel(List<Face> faces) {
        super(new String[]{"Nome", "Tipo", "Custo de Mana", "Editar", "Excluir"}, faces);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return WordUtils.capitalize(list.get(rowIndex).getName());
            case 1:
                return list.get(rowIndex).getTypeLine() != null ? list.get(rowIndex).getTypeLine(): "";
            case 2:
                return list.get(rowIndex).getManaCost();
            case 3:
            case 4:
                return list.get(rowIndex);
            default:
                return "Dados não encontrados";
        }
    }
}