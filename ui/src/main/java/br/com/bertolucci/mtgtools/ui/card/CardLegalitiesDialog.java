package br.com.bertolucci.mtgtools.ui.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.DeckFormat;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;

import javax.swing.*;
import java.awt.event.*;
import java.util.EnumSet;

public class CardLegalitiesDialog extends AbstractDialog {

    private JPanel contentPane;
    private JTextField standardComboBox;
    private JTextField commanderComboBox;
    private JTextField historicComboBox;
    private JTextField legacyComboBox;
    private JTextField modernComboBox;
    private JTextField pioneerComboBox;

    // app
    private Card card;

    public CardLegalitiesDialog(Card card) {
        this.card = card;

        fill();
        init(contentPane, "Legalidades do card");
    }

    @Override
    protected void initListeners() {
    }

    private void fill() {
        standardComboBox.setText(getLegalityString(DeckFormat.STANDARD));
        commanderComboBox.setText(getLegalityString(DeckFormat.COMMANDER));
        historicComboBox.setText(getLegalityString(DeckFormat.HISTORIC));
        legacyComboBox.setText(getLegalityString(DeckFormat.LEGACY));
        modernComboBox.setText(getLegalityString(DeckFormat.MODERN));
        pioneerComboBox.setText(getLegalityString(DeckFormat.PIONEER));
    }

    private String getLegalityString(DeckFormat deckFormat) {
        return this.card.getLegalities()
                .stream()
                .filter(c -> c.getDeckFormat() == deckFormat)
                .findFirst()
                .orElseThrow()
                .getLegality()
                .getTranslatedName();
    }
}
