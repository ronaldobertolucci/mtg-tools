package br.com.bertolucci.mtgtools.ui.part;


import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Part;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;
import org.apache.commons.text.WordUtils;

import java.util.Comparator;
import java.util.List;

public class PartListDialog extends AbstractListDialog<Part> {

    private Card card;

    public PartListDialog(Card card, DeckBuilderService deckBuilderService) {
        super(new PartTableModel(card.getParts()), deckBuilderService);
        this.card = card;

        load();
        init(contentPane, "Partes do card");
    }

    @Override
    protected void update(Part part) {
        new UpdatePartDialog(card, deckBuilderService, part);
        load();
    }

    @Override
    protected void insert() {
        new InsertPartDialog(card, deckBuilderService);
        load();
    }

    @Override
    protected void remove(Part part) {
        if (OptionDialogUtil.showDialog(this,
                "Deseja excluir a parte " + WordUtils.capitalize(part.getName()) + "?") != 0) {
            return;
        }

        if (card.getId() != null) {
            deckBuilderService.removePart(part);
        } else {
            card.getParts().remove(part);
        }

        load();
    }

    @Override
    protected void load() {
        List<Part> parts;

        if (card.getId() != null) {
            parts = deckBuilderService.getPartsByCard(card.getId());
        } else {
            parts = card.getParts();
        }

        parts.sort(Comparator.comparing(Part::getName));
        table.setModel(new PartTableModel(parts));
    }

    @Override
    protected void createUIComponents() {
        table = new PartTable();
    }
}
