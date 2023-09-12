package br.com.bertolucci.mtgtools.ui.face;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FaceDialog extends AbstractDialog {

    protected JPanel contentPane;
    protected JButton addFaceButton;
    protected JButton cancelButton;
    protected JTextField faceNameTextField;
    protected JTextField faceTypeLineTextField;
    protected JTextField faceManaCostTextField;
    protected JTextField facePowerTextField;
    protected JTextField faceToughnessTextField;
    protected JTextField faceLoyaltyTextField;
    protected JTextField faceOracleIdTextField;
    protected JTextArea faceFlavorTextTextArea;
    protected JTextArea faceOracleTextTextArea;
    protected JTextField faceImagePathTextField;

    // app
    protected Card card;
    protected DeckBuilderService deckBuilderService;
    protected Face face;

    public FaceDialog(Card card, DeckBuilderService deckBuilderService, Face face) {
        this.card = card;
        this.deckBuilderService = deckBuilderService;
        this.face = face;
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

    protected Face build() {
        face.setCard(card);
        face.setImageUri(faceImagePathTextField.getText());
        face.setToughness(faceToughnessTextField.getText());
        face.setTypeLine(faceTypeLineTextField.getText());
        face.setFlavorText(faceFlavorTextTextArea.getText());
        face.setLoyalty(faceLoyaltyTextField.getText());
        face.setName(faceNameTextField.getText());
        face.setOracleText(faceOracleTextTextArea.getText());
        face.setPower(facePowerTextField.getText());
        face.setManaCost(faceManaCostTextField.getText());
        face.setOracleId(faceOracleIdTextField.getText());
        return face;
    }
}
