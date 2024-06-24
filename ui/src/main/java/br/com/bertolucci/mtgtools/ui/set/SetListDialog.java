package br.com.bertolucci.mtgtools.ui.set;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;

import java.util.ArrayList;
import java.util.List;

public class SetListDialog extends AbstractListDialog<Set> {

    private List<Set> sets = new ArrayList<>();

    public SetListDialog(DeckBuilderService deckBuilderService) {
        super(new SetTableModel(deckBuilderService.getSets()));

        this.sets.addAll(deckBuilderService.getSets());
        this.sets.sort((s1, s2) -> s1.getReleasedAt().isBefore(s2.getReleasedAt()) ? 1 : -1);

        load();
        init(contentPane, "Sets");
    }

    protected void load() {
        table.setModel(new SetTableModel(sets));
    }

    protected void createUIComponents() {
        table = new SetTable();
    }
}
