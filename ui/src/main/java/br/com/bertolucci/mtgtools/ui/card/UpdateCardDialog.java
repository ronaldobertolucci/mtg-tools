package br.com.bertolucci.mtgtools.ui.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Legalities;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.ui.face.FaceListDialog;
import br.com.bertolucci.mtgtools.ui.part.PartListDialog;
import br.com.bertolucci.mtgtools.ui.ruling.RulingListDialog;

import javax.swing.*;
import java.awt.event.*;

public class UpdateCardDialog extends CardDialog {

    public UpdateCardDialog(Set set, DeckBuilderService deckBuilderService, Card card) {
        super(set, deckBuilderService, card);

        fill(card);
        init(contentPane, "Atualize o card");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCard();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        saveCardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateCard();
            }
        });

    }

    private void updateCard() {
        try {
            deckBuilderService.updateCard(build());
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Insira um formato válido para o Cmc", "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fill(Card card) {
        cardNameTextField.setText(card.getName());
        cardLanguageComboBox.setSelectedItem(card.getLang());
        cardLayoutComboBox.setSelectedItem(card.getLayout());
        cardImageStatusComboBox.setSelectedItem(card.getImageStatus());
        cardBorderColorComboBox.setSelectedItem(card.getBorderColor());
        cardCollectorNumberTextField.setText(card.getCollectorNumber());
        cardDigitalCheckBox.setSelected(card.getIsDigital());
        cardOracleIdTextField.setText(card.getOracleId() != null ? card.getOracleId().toString() : null);

        cardPowerTextField.setText(card.getPower());
        cardToughnessTextField.setText(card.getToughness());
        cardLoyaltyTextField.setText(card.getLoyalty());
        cardTypeLineTextField.setText(card.getTypeLine());
        cardRarityComboBox.setSelectedItem(card.getRarity());
        cardManaCostTextField.setText(card.getManaCost());
        cardCmcTextField.setText(String.valueOf(card.getCmc()));
        cardOracleTextTextArea.setText(card.getOracleText());
        cardFlavorTextTextArea.setText(card.getFlavorText());
        cardImagePathTextField.setText(card.getImageUri());
    }
}
