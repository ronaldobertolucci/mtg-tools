package br.com.bertolucci.mtgtools.ui.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InsertCardDeckDialog extends CardDeckDialog {

    public InsertCardDeckDialog(DeckBuilderService deckBuilderService, CardDeck cardDeck) {
        super(deckBuilderService, cardDeck);

        init(contentPane, "Insira um card no deck");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCardDeckButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertCardDeck();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertCardDeck();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    protected void insertCardDeck() {
        try {
            build();

            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            deckBuilderService.addCardToDeck(cardDeck, relentlessCheckBox.isSelected());
            deckBuilderService.updateDeck(cardDeck.getDeck());
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(null);
        }
    }
}
