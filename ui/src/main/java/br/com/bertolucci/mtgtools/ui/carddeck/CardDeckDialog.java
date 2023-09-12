package br.com.bertolucci.mtgtools.ui.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardDeckDialog extends AbstractDialog {

    protected JPanel contentPane;
    protected JButton addCardDeckButton;
    protected JButton cancelButton;
    protected JTextField quantityTextField;
    protected JCheckBox relentlessCheckBox;

    protected DeckBuilderService deckBuilderService;
    protected CardDeck cardDeck;

    public CardDeckDialog(DeckBuilderService deckBuilderService, CardDeck cardDeck) {
        this.deckBuilderService = deckBuilderService;
        this.cardDeck = cardDeck;
    }

    protected void initListeners() {
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }

    protected CardDeck build() {
        cardDeck.setQuantity(Integer.valueOf(quantityTextField.getText()));
        return cardDeck;
    }
}
