package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;
import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class UpdateDeckDialog extends DeckDialog {

    public UpdateDeckDialog(DeckBuilderService deckBuilderService, Deck deck) {
        super(deckBuilderService, deck);

        fill(deck);
        prepareAddButton();
        init(contentPane, "Atualize as informações do Deck");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        addDeckButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateDeck();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDeck();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void prepareAddButton() {
        addDeckButton.setText("Salvar");
        addDeckButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass()
                .getClassLoader()
                .getResource("images/check16x16_09BA7C.png"))));
    }

    protected void updateDeck() {
        try {
            Deck d = build();
            deckBuilderService.updateDeck(d);
            dispose();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void fill(Deck deck) {
        nameTextField.setText(WordUtils.capitalize(deck.getName()));
        formatComboBox.setSelectedItem(deck.getDeckFormat());
    }

}
