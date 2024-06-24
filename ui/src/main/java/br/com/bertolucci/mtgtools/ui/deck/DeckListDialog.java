package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;
import br.com.bertolucci.mtgtools.ui.AbstractTableModel;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;
import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

public class DeckListDialog extends AbstractDialog {

    protected JPanel contentPane;
    protected JTable table;
    protected JButton addButton;
    protected AbstractTableModel<Deck> tableModel;
    private DeckBuilderService deckBuilderService;

    public DeckListDialog(DeckBuilderService deckBuilderService) {
        this.tableModel = new DeckTableModel(deckBuilderService.getDecks());
        this.deckBuilderService = deckBuilderService;

        load();
        init(contentPane, "Meus decks");
    }

    protected void initListeners() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int tableLength = table.getColumnCount();
                Deck t;
                if (table.columnAtPoint(e.getPoint()) == (tableLength - 2)) {
                    t = (Deck) table.getModel().getValueAt(table.rowAtPoint(e.getPoint()), (tableLength - 2));
                    update(t);
                }
                if (table.columnAtPoint(e.getPoint()) == (tableLength - 1)) {
                    t = (Deck) table.getModel().getValueAt(table.rowAtPoint(e.getPoint()), (tableLength - 1));
                    remove(t);
                }
            }
        });
        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insert();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int tableLength = table.getColumnCount();
                Deck deck;
                if (table.columnAtPoint(e.getPoint()) == (tableLength - 3)) {
                    deck = (Deck) table.getModel().getValueAt(table.rowAtPoint(e.getPoint()), (tableLength - 3));
                    build(deck);
                }
            }
        });
    }

    private void build(Deck deck) {
        new DeckBuilderDialog(deckBuilderService, deck);
        load();
    }

    protected void update(Deck deck) {
        if (OptionDialogUtil.showDialog(this, "Alterar o formato do deck pode remover cards da lista atual, cuja " +
                "legalidade não é permitida no novo formato. Tem certeza que deseja continuar?") != 0) {
            return;
        }

        new UpdateDeckDialog(deckBuilderService, deck);
        load();
    }

    protected void insert() {
        new InsertDeckDialog(deckBuilderService);
        load();
    }

    protected void remove(Deck deck) {
        if (OptionDialogUtil.showDialog(this,
                "Deseja excluir o deck " + WordUtils.capitalize(deck.getName()) + "?") != 0) {
            return;
        }

        deckBuilderService.removeDeck(deck);
        load();
    }

    protected void load() {
        List<Deck> decks;
        decks = deckBuilderService.getDecks();
        decks.sort(Comparator.comparing(Deck::getName));
        table.setModel(new DeckTableModel(decks));
    }

    protected void createUIComponents() {
        table = new DeckTable();
    }
}
