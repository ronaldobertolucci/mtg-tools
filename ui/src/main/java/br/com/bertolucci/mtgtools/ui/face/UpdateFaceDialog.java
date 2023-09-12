package br.com.bertolucci.mtgtools.ui.face;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class UpdateFaceDialog extends FaceDialog {

    public UpdateFaceDialog(Card card, DeckBuilderService deckBuilderService, Face face) {
        super(card, deckBuilderService, face);

        fill(face);
        prepareAddButton();
        init(contentPane, "Atualize a face");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addFaceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateFace();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFace();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void prepareAddButton() {
        addFaceButton.setText("Salvar");
        addFaceButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass()
                .getClassLoader()
                .getResource("images/check16x16_09BA7C.png"))));
    }

    protected void updateFace() {
        try {
            Face f = build();

            if (card.getId() != null) {
                deckBuilderService.updateFace(f);
            }

            dispose();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void fill(Face face) {
        faceImagePathTextField.setText(face.getImageUri());
        faceToughnessTextField.setText(face.getToughness());
        faceTypeLineTextField.setText(face.getTypeLine());
        faceFlavorTextTextArea.setText(face.getFlavorText());
        faceLoyaltyTextField.setText(face.getLoyalty());
        faceNameTextField.setText(face.getName());
        faceOracleTextTextArea.setText(face.getOracleText());
        facePowerTextField.setText(face.getPower());
        faceManaCostTextField.setText(face.getManaCost());
        faceOracleIdTextField.setText(face.getOracleId() != null ? face.getOracleId().toString() : null);
    }

}
