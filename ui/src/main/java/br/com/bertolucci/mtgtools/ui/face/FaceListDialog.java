package br.com.bertolucci.mtgtools.ui.face;


import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;
import br.com.bertolucci.mtgtools.ui.card.CardDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FaceListDialog extends AbstractListDialog<Face> {

    private Card card;

    public FaceListDialog(Card card) {
        super(new FaceTableModel(new ArrayList<>(card.getFaces())));
        this.card = card;

        load();
        init(contentPane, "Faces do card");
    }

    @Override
    protected void load() {
        List<Face> faces = new ArrayList<>(card.getFaces());
        faces.sort(Comparator.comparing(Face::getName));
        table.setModel(new FaceTableModel(faces));
    }

    @Override
    protected void createUIComponents() {
        table = new FaceTable();
    }

    @Override
    protected void initListeners() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Face face;
                if (table.columnAtPoint(e.getPoint()) == 3) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    face = (Face) table.getModel().getValueAt(table.rowAtPoint(e.getPoint()), 3);
                    new FaceDialog(face);
                    setCursor(null);
                }
            }
        });
    }
}
