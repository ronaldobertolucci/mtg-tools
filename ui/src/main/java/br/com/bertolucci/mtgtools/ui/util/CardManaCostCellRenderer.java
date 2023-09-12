package br.com.bertolucci.mtgtools.ui.util;

import br.com.bertolucci.mtgtools.deckbuilder.application.util.ExtractSymbolsService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CardManaCostCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }

        return getPanel(panel, ExtractSymbolsService.extract((String) value));
    }

    private JPanel getPanel(JPanel panel, Map<Integer, List<String>> symbolsMap) {
        if (symbolsMap.isEmpty()) {
            return panel;
        }

        if (symbolsMap.size() == 1) {
            addSymbolsToPanel(panel, symbolsMap.get(0));
            return panel;
        }

        addSymbolsToPanel(panel, symbolsMap.get(0));
        panel.add(new JLabel(" // "));
        addSymbolsToPanel(panel, symbolsMap.get(1));

        return panel;
    }

    private void addSymbolsToPanel(JPanel panel, List<String> symbols) {
        for (String s : symbols) {

            ImageIcon imageIcon = null;
            try {
                if (!Files.exists(Paths.get("app/symbols/" + s + "16.png"))) {
                    throw new Exception();
                }

                imageIcon = new ImageIcon("app/symbols/" + s + "16.png");
            } catch (Exception e) {
                imageIcon = new ImageIcon(Objects.requireNonNull(this.getClass()
                        .getClassLoader()
                        .getResource("images/no-image16.png")));
            }

            panel.add(new JLabel(imageIcon));
        }
    }
}