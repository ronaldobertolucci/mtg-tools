package br.com.bertolucci.mtgtools.ui;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

public abstract class AbstractTable extends JTable {


    public AbstractTable() {
        this.setSelectionMode(SINGLE_SELECTION);
        this.setRowHeight(25);

        customizeHeader();
    }

    private void customizeHeader() {
        this.getTableHeader().setFont(this.getTableHeader().getFont().deriveFont(Font.BOLD));
        this.getTableHeader().setForeground(new Color(150, 150, 150));
    }

    protected abstract void setColumnSizes();

    protected abstract void setButtons();

    @Override
    public void setModel(TableModel tableModel) {
        super.setModel(tableModel);

        if (getModel().getColumnCount() == 0) {
            return;
        }

        setColumnSizes();
        setButtons();
    }
}
