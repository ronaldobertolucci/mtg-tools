package br.com.bertolucci.mtgtools.ui.ruling;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Ruling;

import javax.swing.*;
import java.awt.event.*;
import java.time.format.DateTimeParseException;

public class InsertRulingDialog extends RulingDialog {

    public InsertRulingDialog(Card card, DeckBuilderService deckBuilderService) {
        super(card, deckBuilderService, new Ruling());

        init(contentPane, "Insira uma regra");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addRulingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertRuling();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertRuling();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    protected void insertRuling() {
        try {
            Ruling r = build();

            if (card.getId() != null) {
                deckBuilderService.saveRuling(r);
            } else {
                card.addRuling(r);
            }

            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "A data deve ser inserida no formato dd/mm/aaaa", "Um erro ocorreu",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
