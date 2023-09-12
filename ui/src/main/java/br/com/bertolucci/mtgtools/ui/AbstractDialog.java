package br.com.bertolucci.mtgtools.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class AbstractDialog extends JDialog {

    protected void init(JComponent panel, String title) {
        this.setContentPane(panel);
        this.setModal(true);
        this.setTitle(title);
        this.pack();
        centralize();
        initBasicListeners(panel);
        this.setVisible(true);
    }

    private void initBasicListeners(JComponent panel) {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        panel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        initListeners();
    }

    protected abstract void initListeners();

    protected void centralize() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

}
