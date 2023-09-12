package br.com.bertolucci.mtgtools.ui.set;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;

import java.util.List;

public class SetListDialog extends AbstractListDialog<Set> {

    private List<Set> sets;

    public SetListDialog(DeckBuilderService deckBuilderService) {
        super(new SetTableModel(deckBuilderService.getSets()), deckBuilderService);
        this.sets = deckBuilderService.getSets();

        load();
        addButton.setVisible(false);
        init(contentPane, "Sets");
    }

    @Override
    protected void update(Set set) {
    }

    @Override
    protected void insert() {
    }

    @Override
    protected void remove(Set set) {
    }

    @Override
    protected void load() {
        table.setModel(new SetTableModel(sets));
    }

    @Override
    protected void createUIComponents() {
        table = new SetTable();
    }
}
