package br.com.bertolucci.mtgtools.ui.ruling;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Ruling;

import javax.swing.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class UpdateRulingDialog extends RulingDialog {

    public UpdateRulingDialog(Card card, DeckBuilderService deckBuilderService, Ruling ruling) {
        super(card, deckBuilderService, ruling);

        fill(ruling);
        prepareAddButton();
        init(contentPane, "Atualize a regra");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addRulingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateRuling();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRuling();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void prepareAddButton() {
        addRulingButton.setText("Salvar");
        addRulingButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass()
                .getClassLoader()
                .getResource("images/check16x16_09BA7C.png"))));
    }

    protected void updateRuling() {
        try {
            Ruling r = build();

            if (card.getId() != null) {
                deckBuilderService.updateRuling(r);
            }

            dispose();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "A data deve ser inserida no formato dd/mm/aaaa", "Um erro ocorreu",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void fill(Ruling ruling) {
        rulingSourceTextField.setText(ruling.getSource());
        rulingPublishedAtTextField.setText(ruling.getPublishedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        rulingCommentTextArea.setText(ruling.getComment());
    }

}
