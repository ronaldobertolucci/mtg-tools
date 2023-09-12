package br.com.bertolucci.mtgtools.ui.symbol;

import br.com.bertolucci.mtgtools.ui.AbstractTable;
import br.com.bertolucci.mtgtools.ui.util.SymbolManaCostCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class SymbolTable extends AbstractTable {

    private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

    public SymbolTable() {
        super();

        this.centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    protected void setColumnSizes() {
        this.getColumnModel().getColumn(0).setMinWidth(80);
        this.getColumnModel().getColumn(1).setMinWidth(80);
        this.getColumnModel().getColumn(2).setMinWidth(120);
        this.getColumnModel().getColumn(3).setMinWidth(120);
        this.getColumnModel().getColumn(4).setMinWidth(300);
    }

    @Override
    protected void setButtons() {
        this.getColumnModel().getColumn(0).setCellRenderer(new SymbolManaCostCellRenderer());
        this.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        this.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        this.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        this.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
    }

}