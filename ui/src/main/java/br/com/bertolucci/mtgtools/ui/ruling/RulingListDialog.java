package br.com.bertolucci.mtgtools.ui.ruling;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Ruling;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;

import java.util.Comparator;
import java.util.List;

public class RulingListDialog extends AbstractListDialog<Ruling> {

    private Card card;

    public RulingListDialog(Card card, DeckBuilderService deckBuilderService) {
        super(new RulingTableModel(card.getRulings()), deckBuilderService);
        this.card = card;

        load();
        init(contentPane, "Regras do card");
    }

    @Override
    protected void update(Ruling ruling) {
        new UpdateRulingDialog(card, deckBuilderService, ruling);
        load();
    }

    @Override
    protected void insert() {
        new InsertRulingDialog(card, deckBuilderService);
        load();
    }

    @Override
    protected void remove(Ruling ruling) {
        if (OptionDialogUtil.showDialog(this, "Deseja excluir a regra?") != 0) {
            return;
        }

        if (card.getId() != null) {
            deckBuilderService.removeRuling(ruling);
        } else {
            card.getRulings().remove(ruling);
        }

        load();
    }

    @Override
    protected void load() {
        List<Ruling> rulings;

        if (card.getId() != null) {
            rulings = deckBuilderService.getRulingsByCard(card.getId());
        } else {
            rulings = card.getRulings();
        }

        rulings.sort(Comparator.comparing(Ruling::getPublishedAt));
        table.setModel(new RulingTableModel(rulings));
    }

    @Override
    protected void createUIComponents() {
        table = new RulingTable();
    }
}
