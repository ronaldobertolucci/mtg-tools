package br.com.bertolucci.mtgtools.ui.carddeck;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.application.deck.ValidateCardName;
import br.com.bertolucci.mtgtools.deckbuilder.application.deck.ValidateCardQuantity;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class UpdateCardDeckDialog extends CardDeckDialog {

    public UpdateCardDeckDialog(DeckBuilderService deckBuilderService, CardDeck cardDeck) {
        super(deckBuilderService, cardDeck);

        fill();
        prepareAddButton();
        init(contentPane, "Atualize as informações do card no deck");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addCardDeckButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateCardDeck();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCardDeck();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void prepareAddButton() {
        addCardDeckButton.setText("Salvar");
        addCardDeckButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass()
                .getClassLoader()
                .getResource("images/check16x16_09BA7C.png"))));
    }

    protected void updateCardDeck() {
        try {
            build();

            if (ValidateCardQuantity.isValid(cardDeck, relentlessCheckBox.isSelected())
                    && ValidateCardName.isValidOnUpdate(cardDeck)) {
                deckBuilderService.updateDeck(cardDeck.getDeck());
                dispose();
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
            deckBuilderService.reloadCardDeck(cardDeck);
        }

    }

    protected void fill() {
        quantityTextField.setText(String.valueOf(cardDeck.getQuantity()));
        if (cardDeck.getQuantity() > 4) {
            relentlessCheckBox.setSelected(true);
        }
    }

}
