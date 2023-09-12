package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;
import org.apache.commons.text.WordUtils;

import java.util.Comparator;
import java.util.List;

public class DeckListDialog extends AbstractListDialog<Deck> {

    public DeckListDialog(DeckBuilderService deckBuilderService) {
        super(new DeckTableModel(deckBuilderService.getDecks()), deckBuilderService);

        load();
        init(contentPane, "Meus decks");
    }

    @Override
    protected void update(Deck deck) {
        new UpdateDeckDialog(deckBuilderService, deck);
        load();
    }

    @Override
    protected void insert() {
        new InsertDeckDialog(deckBuilderService);
        load();
    }

    @Override
    protected void remove(Deck deck) {
        if (OptionDialogUtil.showDialog(this,
                "Deseja excluir o deck " + WordUtils.capitalize(deck.getName()) + "?") != 0) {
            return;
        }

        deckBuilderService.removeDeck(deck);
        load();
    }

    @Override
    protected void load() {
        List<Deck> decks;
        decks = deckBuilderService.getDecks();
        decks.sort(Comparator.comparing(Deck::getName));
        table.setModel(new DeckTableModel(decks));
    }

    @Override
    protected void createUIComponents() {
        table = new DeckTable();
    }
}
