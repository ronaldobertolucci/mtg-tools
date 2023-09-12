package br.com.bertolucci.mtgtools.ui.ruling;

import br.com.bertolucci.mtgtools.ui.AbstractTable;
import br.com.bertolucci.mtgtools.ui.util.ButtonCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class RulingTable extends AbstractTable {

    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    public RulingTable() {
        super();

        this.centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    protected void setColumnSizes() {
        this.getColumnModel().getColumn(0).setMinWidth(100);
        this.getColumnModel().getColumn(1).setMinWidth(100);
        this.getColumnModel().getColumn(2).setMinWidth(460);
        this.getColumnModel().getColumn(3).setMaxWidth(60);
        this.getColumnModel().getColumn(4).setMaxWidth(60);
    }

    @Override
    protected void setButtons() {
        this.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        this.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        this.getColumnModel().getColumn(3).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/pencil16x16.png")));
        this.getColumnModel().getColumn(4).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/trash16x16.png")));
    }

}