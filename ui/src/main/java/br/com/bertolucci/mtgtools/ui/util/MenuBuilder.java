package br.com.bertolucci.mtgtools.ui.util;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuBuilder {

    private JMenuBar menuBar;
    private List<JMenuItem> menuItemList;

    public MenuBuilder() {
        this.menuBar = new JMenuBar();
    }

    public MenuBuilder addMenu(String title) {
        menuBar.add(new JMenu(title));
        return this;
    }

    public MenuBuilder addMenuItem(int menuIndex, String title, ActionListener actionListener) {
        menuBar.getMenu(menuIndex).add(getItem(title, actionListener));
        return this;
    }

    public MenuBuilder addSeparator(int menuIndex) {
        menuBar.getMenu(menuIndex).addSeparator();
        return this;
    }

    private JMenuItem getItem(String title, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    public JMenuBar build() {
        return menuBar;
    }
}
