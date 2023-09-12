package br.com.bertolucci.mtgtools.ui.part;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Part;

import javax.swing.*;
import java.awt.event.*;

public class InsertPartDialog extends PartDialog {

    public InsertPartDialog(Card card, DeckBuilderService deckBuilderService) {
        super(card, deckBuilderService, new Part());

        init(contentPane, "Insira uma parte");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addPartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertPart();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertPart();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    protected void insertPart() {
        try {
            Part p = build();

            if (card.getId() != null) {
                deckBuilderService.savePart(p);
            } else {
                card.addPart(p);
            }

            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        }
    }
}
