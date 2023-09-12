package br.com.bertolucci.mtgtools.ui;

import javax.swing.*;

public class Main {
    private JPanel contentPane;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MTG Construtor de Decks");
        frame.setContentPane(new Main().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        new InitDialog(frame);
        frame.setVisible(true);
    }
}
