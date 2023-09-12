package br.com.bertolucci.mtgtools.ui;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AbstractListDialog<T> extends AbstractDialog {

    protected JPanel contentPane;
    protected JTable table;
    protected JButton addButton;
    protected AbstractTableModel<T> tableModel;
    protected DeckBuilderService deckBuilderService;

    public AbstractListDialog(AbstractTableModel<T> tableModel, DeckBuilderService deckBuilderService) {
        this.tableModel = tableModel;
        this.deckBuilderService = deckBuilderService;
    }

    protected void initListeners() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int tableLength = table.getColumnCount();
                T t;
                if (table.columnAtPoint(e.getPoint()) == (tableLength - 2)) {
                    t = (T) table.getModel().getValueAt(table.rowAtPoint(e.getPoint()), (tableLength - 2));
                    update(t);
                }
                if (table.columnAtPoint(e.getPoint()) == (tableLength - 1)) {
                    t = (T) table.getModel().getValueAt(table.rowAtPoint(e.getPoint()), (tableLength - 1));
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
    }

    protected abstract void remove(T t);

    protected abstract void update(T t);

    protected abstract void insert();

    protected abstract void load();

    protected abstract void createUIComponents();
}
