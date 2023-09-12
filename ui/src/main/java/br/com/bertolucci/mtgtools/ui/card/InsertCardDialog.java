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

public class InsertCardDialog extends CardDialog {

    public InsertCardDialog(Set selectedSet, DeckBuilderService deckBuilderService) {
        super(selectedSet, deckBuilderService, new Card());

        init(contentPane, "Insira um card");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertCard();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        saveCardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertCard();
            }
        });

    }

    private void insertCard() {
        try {
            deckBuilderService.saveCard(build());
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Insira um formato válido para o Cmc", "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        }
    }
}
