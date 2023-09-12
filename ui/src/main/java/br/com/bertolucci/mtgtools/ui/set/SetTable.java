package br.com.bertolucci.mtgtools.ui.set;

import br.com.bertolucci.mtgtools.ui.AbstractTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class SetTable extends AbstractTable {

    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    public SetTable() {
        super();

        this.centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    protected void setColumnSizes() {
        this.getColumnModel().getColumn(0).setMinWidth(300);
        this.getColumnModel().getColumn(1).setMinWidth(50);
        this.getColumnModel().getColumn(2).setMinWidth(100);
        this.getColumnModel().getColumn(3).setMinWidth(180);
        this.getColumnModel().getColumn(4).setMinWidth(100);
    }

    @Override
    protected void setButtons() {
        for (int i = 1; i <= 4; i++) {
            this.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

}