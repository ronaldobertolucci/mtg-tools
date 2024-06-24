package br.com.bertolucci.mtgtools.ui;

import javax.swing.*;

public abstract class AbstractListDialog<T> extends AbstractDialog {

    protected JPanel contentPane;
    protected JTable table;
    protected AbstractTableModel<T> tableModel;

    public AbstractListDialog(AbstractTableModel<T> tableModel) {
        this.tableModel = tableModel;
    }

    @Override
    protected void initListeners() {
    }

    protected abstract void load();

    protected abstract void createUIComponents();
}
