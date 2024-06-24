package br.com.bertolucci.mtgtools.ui.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;
import br.com.bertolucci.mtgtools.ui.face.FaceListDialog;
import com.google.common.io.Resources;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.event.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CardDialog extends AbstractDialog {

    protected JPanel contentPane;
    protected JTextField cardNameTextField;
    protected JTextField cardPowerTextField;
    protected JTextField cardToughnessTextField;
    protected JTextField cardTypeLineTextField;
    protected JTextField cardLoyaltyTextField;
    protected JTextField cardManaCostTextField;
    protected JTextArea cardOracleTextTextArea;
    protected JTextField cardCollectorNumberTextField;
    protected JLabel infoLabel;
    protected JTextField cardCmcTextField;
    protected JLabel infoCmcLabel;
    private JButton legalitiesButton;
    private JButton facesButton;
    private JTextField cardImageStatusTextField;
    private JTextField cardRarityTextField;
    private JLabel infoImageLabel;

    protected Card card;

    public CardDialog(Card card) {
        this.card = card;

        fill(this.card);
        init(contentPane, "Detalhes do card");
    }

    @Override
    protected void initListeners() {
        infoLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                URL url = Resources.getResource("texts/mana_cost.txt");
                String text = Resources.toString(url, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(contentPane, text,"Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoCmcLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                URL url = Resources.getResource("texts/cmc.txt");
                String text = Resources.toString(url, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(contentPane, text,"Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        legalitiesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadLegalities();
            }
        });

        facesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadFaces();
            }
        });
    }

    protected void loadFaces() {
        new FaceListDialog(card);
    }

    protected void loadLegalities() {
        new CardLegalitiesDialog(card);
    }

    private void fill(Card card) {
        cardNameTextField.setText(card.getName());
        cardImageStatusTextField.setText(card.getImageStatus().getTranslatedName());
        cardCollectorNumberTextField.setText(card.getCollectorNumber());
        cardPowerTextField.setText(card.getPower());
        cardToughnessTextField.setText(card.getToughness());
        cardLoyaltyTextField.setText(card.getLoyalty());
        cardTypeLineTextField.setText(card.getTypeLine());
        cardRarityTextField.setText(card.getCardRarity().getTranslatedName());
        cardManaCostTextField.setText(card.getManaCost());
        cardCmcTextField.setText(String.valueOf(card.getCmc()));
        cardOracleTextTextArea.setText(card.getOracleText());
    }
}
