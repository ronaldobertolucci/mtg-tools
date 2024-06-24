package br.com.bertolucci.mtgtools.ui;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.ui.deck.DeckListDialog;
import br.com.bertolucci.mtgtools.ui.set.SetListDialog;
import br.com.bertolucci.mtgtools.ui.set.SetManagerDialog;
import br.com.bertolucci.mtgtools.ui.symbol.SymbolListDialog;
import br.com.bertolucci.mtgtools.ui.util.MenuBuilder;

import javax.swing.*;
import java.awt.*;

public class MainMenu {

    private final MenuBuilder menuBuilder = new MenuBuilder();
    private DeckBuilderService deckBuilderService;
    private JFrame frame;

    public MainMenu(JFrame frame, DeckBuilderService deckBuilderService) {
        this.frame = frame;
        this.deckBuilderService = deckBuilderService;

        createFileMenu(menuBuilder);
        createCollectionMenu(menuBuilder);
        createDeckBuilderMenu(menuBuilder);
        this.frame.setJMenuBar(menuBuilder.build());
    }

    private void createCollectionMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenu("Coleção");
        menuBuilder.addMenuItem(1, "Meus Cards", (actionEvent) -> {
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            new SetManagerDialog(deckBuilderService);
            frame.setCursor(null);
        });
        menuBuilder.addSeparator(1);
        menuBuilder.addMenuItem(1, "Sets", (actionEvent) -> {
            new SetListDialog(deckBuilderService);
        });
        menuBuilder.addMenuItem(1, "Símbolos", (actionEvent) -> {
            new SymbolListDialog(deckBuilderService);
        });
    }

    private void createFileMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenu("Arquivo");
        menuBuilder.addMenuItem(0, "Sair", (actionEvent) -> frame.dispose());
    }

    private void createDeckBuilderMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenu("Construtor de decks");
        menuBuilder.addMenuItem(2, "Meus decks", (actionEvent) -> {
            new DeckListDialog(deckBuilderService);
        });
    }
}
