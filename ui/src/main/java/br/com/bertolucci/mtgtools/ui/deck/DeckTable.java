package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.ui.AbstractTable;
import br.com.bertolucci.mtgtools.ui.util.ButtonCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class DeckTable extends AbstractTable {

    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    public DeckTable() {
        super();

        this.centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    protected void setColumnSizes() {
        this.getColumnModel().getColumn(0).setMinWidth(150);
        this.getColumnModel().getColumn(1).setMinWidth(150);
        this.getColumnModel().getColumn(2).setMinWidth(100);
        this.getColumnModel().getColumn(3).setMaxWidth(80);
        this.getColumnModel().getColumn(4).setMaxWidth(80);
    }

    @Override
    protected void setButtons() {
        this.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        this.getColumnModel().getColumn(2).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/external-link14_555.png")));
        this.getColumnModel().getColumn(3).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/pencil16x16.png")));
        this.getColumnModel().getColumn(4).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/trash16x16.png")));
    }

}
