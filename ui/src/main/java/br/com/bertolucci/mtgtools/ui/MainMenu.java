package br.com.bertolucci.mtgtools.ui;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.pngsvgtools.DownloadImageService;
import br.com.bertolucci.mtgtools.ui.download.DownloadCardsDialog;
import br.com.bertolucci.mtgtools.ui.set.SetListDialog;
import br.com.bertolucci.mtgtools.ui.set.SetManagerDialog;
import br.com.bertolucci.mtgtools.ui.symbol.SymbolListDialog;
import br.com.bertolucci.mtgtools.ui.util.MenuBuilder;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;

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
        this.frame.setJMenuBar(menuBuilder.build());
    }

    private void createCollectionMenu(MenuBuilder menuBuilder) {
        menuBuilder.addMenu("Coleção");
        menuBuilder.addMenuItem(1, "Meus Cards", (actionEvent) -> {
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            new SetManagerDialog(deckBuilderService);
            frame.setCursor(null);
        });
        menuBuilder.addMenuItem(1, "Buscar Cards na API", (actionEvent) -> {
            frame.getContentPane().setVisible(false);
            if (OptionDialogUtil.showDialog(frame, "Deseja procurar cards na API") == 0) {
                new DownloadCardsDialog(deckBuilderService);
            }
            frame.getContentPane().setVisible(true);
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
}
