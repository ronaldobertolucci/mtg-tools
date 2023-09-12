package br.com.bertolucci.mtgtools.ui.part;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Part;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.PartType;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.Objects;

public class PartDialog extends AbstractDialog {
    protected JPanel contentPane;
    protected JButton addPartButton;
    protected JButton cancelButton;
    protected JTextField partNameTextField;
    protected JTextField partTypeLineTextField;
    protected JComboBox partTypeComboBox;

    //app
    protected Card card;
    protected Part part;
    protected DeckBuilderService deckBuilderService;

    public PartDialog(Card card, DeckBuilderService deckBuilderService, Part part) {
        this.card = card;
        this.deckBuilderService = deckBuilderService;
        this.part = part;

        prepareComboBox();
    }

    private void prepareComboBox() {
        for (PartType partType : EnumSet.allOf(PartType.class)) {
            partTypeComboBox.addItem(partType);
        }
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

    protected Part build() {
        PartType partType = (PartType) Objects.requireNonNull(partTypeComboBox.getSelectedItem());

        part.setCard(card);
        part.setTypeLine(partTypeLineTextField.getText());
        part.setName(partNameTextField.getText());
        part.setType(partType.name().toUpperCase());
        return part;
    }
}