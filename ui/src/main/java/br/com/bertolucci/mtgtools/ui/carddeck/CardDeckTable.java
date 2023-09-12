package br.com.bertolucci.mtgtools.ui.carddeck;

import br.com.bertolucci.mtgtools.ui.AbstractTable;
import br.com.bertolucci.mtgtools.ui.util.ButtonCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class CardDeckTable extends AbstractTable {

    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    public CardDeckTable() {
        super();

        this.centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    protected void setColumnSizes() {
        this.getColumnModel().getColumn(1).setMaxWidth(60);
        this.getColumnModel().getColumn(2).setMaxWidth(60);
        this.getColumnModel().getColumn(3).setMaxWidth(60);
    }

    @Override
    protected void setButtons() {
        this.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        this.getColumnModel().getColumn(2).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/pencil16x16.png")));
        this.getColumnModel().getColumn(3).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/trash16x16.png")));
    }

}
