package br.com.bertolucci.mtgtools.ui;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderServiceImpl;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;

import javax.swing.*;

public class Main {
    private JPanel contentPane;

    public static void main(String[] args) {

        JFrame frame = new JFrame("MTG Construtor de Decks");
        frame.setContentPane(new Main().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        try {
            DeckBuilderService deckBuilderService = new DeckBuilderServiceImpl();
            new MainMenu(frame, deckBuilderService);
        } catch (NoApiConnectionException | InterruptedException | ImageDownloadException e) {
            throw new RuntimeException(e.getMessage());
        }

        frame.setVisible(true);
    }
}
