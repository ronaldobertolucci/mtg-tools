package br.com.bertolucci.mtgtools.ui.face;


import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;
import org.apache.commons.text.WordUtils;

import java.util.Comparator;
import java.util.List;

public class FaceListDialog extends AbstractListDialog<Face> {

    private Card card;

    public FaceListDialog(Card card, DeckBuilderService deckBuilderService) {
        super(new FaceTableModel(card.getFaces()), deckBuilderService);
        this.card = card;

        load();
        init(contentPane, "Faces do card");
    }

    @Override
    protected void update(Face face) {
        new UpdateFaceDialog(card, deckBuilderService, face);
        load();
    }

    @Override
    protected void insert() {
        new InsertFaceDialog(card, deckBuilderService);
        load();
    }

    @Override
    protected void remove(Face face) {
        if (OptionDialogUtil.showDialog(this,
                "Deseja excluir a face " + WordUtils.capitalize(face.getName()) + "?") != 0) {
            return;
        }

        if (card.getId() != null) {
            deckBuilderService.removeFace(face);
        } else {
            card.getFaces().remove(face);
        }

        load();
    }

    @Override
    protected void load() {
        List<Face> faces;

        if (card.getId() != null) {
            faces = deckBuilderService.getFacesByCard(card.getId());
        } else {
            faces = card.getFaces();
        }

        faces.sort(Comparator.comparing(Face::getName));
        table.setModel(new FaceTableModel(faces));
    }

    @Override
    protected void createUIComponents() {
        table = new FaceTable();
    }
}
