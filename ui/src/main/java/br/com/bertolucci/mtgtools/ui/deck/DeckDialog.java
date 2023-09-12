package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Format;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.Objects;

public class DeckDialog extends AbstractDialog {

    protected JPanel contentPane;
    protected JButton addDeckButton;
    protected JButton cancelButton;
    protected JTextField nameTextField;
    protected JComboBox<Format> formatComboBox;
    protected Deck deck;
    protected DeckBuilderService deckBuilderService;

    public DeckDialog(DeckBuilderService deckBuilderService, Deck deck) {
        this.deck = deck;
        this.deckBuilderService = deckBuilderService;

        prepareComboBox();
    }

    private void prepareComboBox() {
        for (Format format : EnumSet.allOf(Format.class)) {
            formatComboBox.addItem(format);
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

    protected Deck build() {
        Format format = (Format) Objects.requireNonNull(formatComboBox.getSelectedItem());

        deck.setName(nameTextField.getText());
        deck.setFormat(format.name().toUpperCase());
        return deck;
    }
}
