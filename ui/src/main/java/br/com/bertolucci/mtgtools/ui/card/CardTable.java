package br.com.bertolucci.mtgtools.ui.card;

import br.com.bertolucci.mtgtools.ui.AbstractTable;
import br.com.bertolucci.mtgtools.ui.util.ButtonCellRenderer;
import br.com.bertolucci.mtgtools.ui.util.CardManaCostCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class CardTable extends AbstractTable {

    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    public CardTable() {
        super();

        this.centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    protected void setColumnSizes() {
        this.getColumnModel().getColumn(0).setMinWidth(200);
        this.getColumnModel().getColumn(1).setMinWidth(230);
        this.getColumnModel().getColumn(2).setMinWidth(200);
        this.getColumnModel().getColumn(5).setMaxWidth(60);
    }

    @Override
    protected void setButtons() {
        this.getColumnModel().getColumn(2).setCellRenderer(new CardManaCostCellRenderer());
        this.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        this.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        this.getColumnModel().getColumn(5).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/eye16x16_555.png")));
    }

}
