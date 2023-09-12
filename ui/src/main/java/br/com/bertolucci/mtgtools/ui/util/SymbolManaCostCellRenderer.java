package br.com.bertolucci.mtgtools.ui.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class SymbolManaCostCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }

        String symbol = (String) value;

        ImageIcon imageIcon = null;
        try {
            if (!Files.exists(Paths.get("app/symbols/" + symbol.replace("/", "") + "16.png"))) {
                throw new Exception();
            }

            imageIcon = new ImageIcon("app/symbols/" + symbol.replace("/", "") + "16.png");
        } catch (Exception e) {
            imageIcon = new ImageIcon(Objects.requireNonNull(this.getClass()
                    .getClassLoader()
                    .getResource("images/no-image16.png")));
        }

        panel.add(new JLabel(imageIcon));

        return panel;
    }
}