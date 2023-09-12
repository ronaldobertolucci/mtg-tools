package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.ui.AbstractTable;
import br.com.bertolucci.mtgtools.ui.util.ButtonCellRenderer;
import br.com.bertolucci.mtgtools.ui.util.CardManaCostCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class DeckBuilderCardTable extends AbstractTable {
    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    public DeckBuilderCardTable() {
        super();

        this.centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    protected void setColumnSizes() {
        this.getColumnModel().getColumn(4).setMaxWidth(60);
        this.getColumnModel().getColumn(5).setMaxWidth(60);
        this.getColumnModel().getColumn(6).setMaxWidth(60);
        this.getColumnModel().getColumn(7).setMaxWidth(60);
        this.getColumnModel().getColumn(8).setMaxWidth(60);
        this.getColumnModel().getColumn(9).setMaxWidth(60);
    }

    @Override
    protected void setButtons() {
        this.getColumnModel().getColumn(3).setCellRenderer(new CardManaCostCellRenderer());
        this.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        this.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        this.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        this.getColumnModel().getColumn(7).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/external-link14_555.png")));
        this.getColumnModel().getColumn(8).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/eye16x16_555.png")));
        this.getColumnModel().getColumn(9).setCellRenderer(new ButtonCellRenderer(this.getClass()
                .getClassLoader()
                .getResource("images/plus16x16_09BA7C.png")));
    }
}
