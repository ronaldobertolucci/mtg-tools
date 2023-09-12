package br.com.bertolucci.mtgtools.ui.face;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;

import javax.swing.*;
import java.awt.event.*;

public class InsertFaceDialog extends FaceDialog{

    public InsertFaceDialog(Card card, DeckBuilderService deckBuilderService) {
        super(card, deckBuilderService, new Face());

        init(contentPane, "Insira uma face");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addFaceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertFace();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertFace();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    protected void insertFace() {
        try {
            Face f = build();

            if (card.getId() != null) {
                deckBuilderService.saveFace(f);
            } else {
                card.addFace(f);
            }

            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        }
    }
}
