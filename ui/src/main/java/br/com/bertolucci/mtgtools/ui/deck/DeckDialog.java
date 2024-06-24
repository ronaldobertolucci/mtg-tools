package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;
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
    protected JComboBox<DeckFormat> formatComboBox;
    protected Deck deck;
    protected DeckBuilderService deckBuilderService;

    public DeckDialog(DeckBuilderService deckBuilderService, Deck deck) {
        this.deck = deck;
        this.deckBuilderService = deckBuilderService;

        prepareComboBox();
    }

    private void prepareComboBox() {
        for (DeckFormat format : EnumSet.allOf(DeckFormat.class)) {
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
        DeckFormat format = (DeckFormat) Objects.requireNonNull(formatComboBox.getSelectedItem());

        deck.setName(nameTextField.getText());
        deck.setDeckFormat(format);
        return deck;
    }
}
