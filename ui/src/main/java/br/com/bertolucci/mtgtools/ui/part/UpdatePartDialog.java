package br.com.bertolucci.mtgtools.ui.part;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Part;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class UpdatePartDialog extends PartDialog {

    public UpdatePartDialog(Card card, DeckBuilderService deckBuilderService, Part part) {
        super(card, deckBuilderService, part);

        fill(part);
        prepareAddButton();
        init(contentPane, "Atualize a parte");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addPartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updatePart();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePart();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void prepareAddButton() {
        addPartButton.setText("Salvar");
        addPartButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass()
                .getClassLoader()
                .getResource("images/check16x16_09BA7C.png"))));
    }

    protected void updatePart() {
        try {
            Part p = build();

            if (card.getId() != null) {
                deckBuilderService.updatePart(p);
            }

            dispose();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void fill(Part part) {
        partTypeLineTextField.setText(part.getTypeLine());
        partNameTextField.setText(part.getName());
        partTypeComboBox.setSelectedItem(part.getType());
    }

}
