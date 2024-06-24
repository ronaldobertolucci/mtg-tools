package br.com.bertolucci.mtgtools.ui.face;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;

import javax.swing.*;

public class FaceDialog extends AbstractDialog {

    protected JPanel contentPane;
    protected JTextField faceNameTextField;
    protected JTextField faceTypeLineTextField;
    protected JTextField faceManaCostTextField;
    protected JTextField facePowerTextField;
    protected JTextField faceToughnessTextField;
    protected JTextField faceLoyaltyTextField;
    protected JTextArea faceOracleTextTextArea;

    // app
    protected Face face;

    public FaceDialog(Face face) {
        this.face = face;

        fill();
        init(contentPane, "Detalhes da face");
    }

    protected void fill() {
        faceNameTextField.setText(face.getName());
        faceTypeLineTextField.setText(face.getTypeLine());
        faceManaCostTextField.setText(face.getManaCost());
        facePowerTextField.setText(face.getPower());
        faceToughnessTextField.setText(face.getToughness());
        faceLoyaltyTextField.setText(face.getLoyalty());
        faceOracleTextTextArea.setText(face.getOracleText());
    }

    @Override
    protected void initListeners() {
    }
}
