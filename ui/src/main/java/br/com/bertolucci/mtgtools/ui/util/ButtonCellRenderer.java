package br.com.bertolucci.mtgtools.ui.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.net.URL;

public class ButtonCellRenderer extends DefaultTableCellRenderer {

    private URL url;

    public ButtonCellRenderer(URL url) {
        this.url = url;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
        label.setHorizontalAlignment(CENTER);
        label.setIcon(new ImageIcon(url));
        label.setText("");
        return label;
    }
}