package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;

import javax.swing.*;
import java.awt.event.*;

public class InsertDeckDialog extends DeckDialog {

    public InsertDeckDialog(DeckBuilderService deckBuilderService) {
        super(deckBuilderService, new Deck());

        init(contentPane, "Insira um deck");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addDeckButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertDeck();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertDeck();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    protected void insertDeck() {
        try {
            Deck d = build();

            deckBuilderService.saveDeck(d);
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        }
    }
}
