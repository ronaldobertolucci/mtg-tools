package br.com.bertolucci.mtgtools.ui;

import java.util.List;

public abstract class AbstractTableModel<T> extends javax.swing.table.AbstractTableModel {

    protected String[] columns;

    protected List<T> list;

    public AbstractTableModel(String[] columns, List<T> list) {
        this.list = list;
        this.columns = columns;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (list.isEmpty()) {
            return Object.class;
        }
        return this.getValueAt(0, columnIndex).getClass();
    }
}
