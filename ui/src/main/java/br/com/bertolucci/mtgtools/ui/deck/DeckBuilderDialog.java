package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Rarity;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;
import br.com.bertolucci.mtgtools.ui.card.CardDetailDialog;
import br.com.bertolucci.mtgtools.ui.carddeck.CardDeckTable;
import br.com.bertolucci.mtgtools.ui.carddeck.CardDeckTableModel;
import br.com.bertolucci.mtgtools.ui.carddeck.InsertCardDeckDialog;
import br.com.bertolucci.mtgtools.ui.carddeck.UpdateCardDeckDialog;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;
import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DeckBuilderDialog extends AbstractDialog {
    private JPanel contentPane;
    private JTextField manaCostTextField;
    private JTextField powerTextField;
    private JTextField toughnessTextField;
    private JTextField loyaltyTextField;
    private JTextField nameTextField;
    private JComboBox setComboBox;
    private JTextField typeTextField;
    private JTextField oracleTextTextField;
    private JTable cardsTable;
    private JButton searchButton;
    private JButton cleanButton;
    private JLabel infoTypeLabel;
    private JLabel infoOracleTextLabel;
    private JTable cardDeckTable;
    private JLabel deckNameLabel;
    private JLabel formatLabel;
    private JLabel totalCardsLabel;
    private JCheckBox blackCheckBox;
    private JCheckBox greenCheckBox;
    private JCheckBox redCheckBox;
    private JCheckBox blueCheckBox;
    private JCheckBox whiteCheckBox;
    private JTextField cmcTextField;
    private JComboBox rarityComboBox;
    private JLabel infoManaCostLabel;
    private JCheckBox showAllCheckBox;
    private JLabel cardsFoundLabel;

    private DeckBuilderService deckBuilderService;
    private List<Set> sets;
    private Deck deck;

    public DeckBuilderDialog(DeckBuilderService deckBuilderService, Deck deck) {
        this.deckBuilderService = deckBuilderService;
        this.sets = deckBuilderService.getSets();
        this.deck = deck;

        prepareComboBoxes();
        load();
        init(contentPane, "Construir deck");
    }

    private void prepareComboBoxes() {
        prepareSetComboBox();
        prepareRarityComboBox();
    }

    private void prepareSetComboBox() {
        setComboBox.addItem("Selecione um set");
        sets.forEach(set -> setComboBox.addItem(set));
        setComboBox.setPrototypeDisplayValue("");
    }

    private void prepareRarityComboBox() {
        rarityComboBox.addItem("Selecione uma raridade");
        for (Rarity rarity : EnumSet.allOf(Rarity.class)) {
            rarityComboBox.addItem(rarity);
        }
        rarityComboBox.setPrototypeDisplayValue("");
    }

    private void load() {
        AtomicInteger totalCards = new AtomicInteger();
        List<CardDeck> cards = deckBuilderService.getCardDeckByDeck(deck.getId());
        cards.forEach(cardDeck -> {
            totalCards.addAndGet(cardDeck.getQuantity());
        });

        deckNameLabel.setText(WordUtils.capitalize(deck.getName()));
        formatLabel.setText(WordUtils.capitalize(deck.getFormat().getTranslatedName()));
        totalCardsLabel.setText(String.valueOf(totalCards));
        cards.sort(Comparator.comparing(cardDeck -> cardDeck.getCard().getName()));
        cardDeckTable.setModel(new CardDeckTableModel(cards));
    }

    @Override
    protected void initListeners() {
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                search();
            }
        });

        cleanButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                clean();
            }
        });

        cardsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Card card;
                switch (cardsTable.columnAtPoint(e.getPoint())) {
                    case 7:
                        card = (Card) cardsTable.getModel().getValueAt(cardsTable.rowAtPoint(e.getPoint()), 7);
                        JOptionPane.showMessageDialog(contentPane, card.getOracleText(),
                                "Texto de habilidade", JOptionPane.PLAIN_MESSAGE, new ImageIcon());
                        break;
                    case 8:
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                        card = (Card) cardsTable.getModel().getValueAt(cardsTable.rowAtPoint(e.getPoint()), 8);
                        new CardDetailDialog(card, deckBuilderService);

                        setCursor(null);
                        break;
                    case 9:
                        card = (Card) cardsTable.getModel().getValueAt(cardsTable.rowAtPoint(e.getPoint()), 9);
                        insertCardDeck(card, deck);
                }
            }
        });

        cardDeckTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CardDeck cardDeck;
                switch (cardDeckTable.columnAtPoint(e.getPoint())) {
//                    case 2:
//                        cardDeck = (CardDeck) cardDeckTable.getModel().getValueAt(cardDeckTable.rowAtPoint(e.getPoint()), 2);
//                        update( cardDeck);
//                        break;
                    case 2:
                        cardDeck = (CardDeck) cardDeckTable.getModel().getValueAt(cardDeckTable.rowAtPoint(e.getPoint()), 2);
                        removeCardDeck(cardDeck);
                }
            }
        });

        infoTypeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(contentPane, """
                                O tipo deve ser buscado por palavras isoladas.
                                                        
                                Quando mais de uma palavra é usada, se houver resultado, os cards resultantes
                                possuirão todas as palavras usadas na pesquisa.""",
                        "Busca de tipos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoOracleTextLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(contentPane, """
                                O campo de palavras-chaves pode ser usado para buscar cards pelo seu texto de habilidade.
                                                        
                                Quando mais de uma palavra é usada, se houver resultado, os cards resultantes possuirão
                                todas as palavras usadas na pesquisa. Usar 'flying haste', por exemplo, retorna todos os
                                cards que possuem ambas habilidades.""",
                        "Busca de palavras-chave", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoManaCostLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(contentPane, """
                                O filtro por custo de mana segue as mesmas regras já vistas anteriormente, ou seja, {1}{W}
                                buscará cards com custo de mana igual a: 'uma mana sem cor e uma mana branca'.
                                                
                                Quando o custo de mana é especificado na busca, serão retornados cards com custo exatamente
                                igual ao informado. Entretanto, é possível fornecer espaços 'coringa' usando o caractere _.
                                Por exemplo, com o filtro {1}{R}{_} é possível retornar todos os cards com o custo de mana
                                'uma mana sem cor, uma mana vermelha e qualquer outra mana válida ({U}, {B}, {W}, etc).""",
                        "Busca de palavras-chave", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    protected void insertCardDeck(Card card, Deck deck) {
        new InsertCardDeckDialog(deckBuilderService, new CardDeck(card, deck));
        load();
    }

    private void update(CardDeck cardDeck) {
        new UpdateCardDeckDialog(deckBuilderService, cardDeck);
        load();
    }

    private void removeCardDeck(CardDeck cardDeck) {
        if (OptionDialogUtil.showDialog(this, "Deseja remover o card do deck?") != 0) {
            return;
        }

        deckBuilderService.removeCardDeck(cardDeck);
        load();
    }

    private void clean() {
        blackCheckBox.setSelected(false);
        greenCheckBox.setSelected(false);
        redCheckBox.setSelected(false);
        blueCheckBox.setSelected(false);
        whiteCheckBox.setSelected(false);
        showAllCheckBox.setSelected(false);

        nameTextField.setText(null);
        setComboBox.setSelectedIndex(0);
        rarityComboBox.setSelectedIndex(0);
        oracleTextTextField.setText(null);
        typeTextField.setText(null);
        manaCostTextField.setText(null);
        powerTextField.setText(null);
        toughnessTextField.setText(null);
        loyaltyTextField.setText(null);
        cmcTextField.setText(null);
    }

    private void search() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Set set = setComboBox.getSelectedIndex() != 0 ? (Set) setComboBox.getSelectedItem() : null;
        Rarity rarity = rarityComboBox.getSelectedIndex() != 0 ? (Rarity) rarityComboBox.getSelectedItem() : null;

        Double cmc = null;
        try {
            cmc = Double.valueOf(cmcTextField.getText());
        } catch (NumberFormatException ignored) {
        }

        String format = showAllCheckBox.isSelected() ? null : deck.getFormat().name().toLowerCase();

        List<Card> cards = deckBuilderService.findCards(
                nameTextField.getText(), set != null ? set.getId() : null, getSplit(oracleTextTextField.getText()),
                getSplit(typeTextField.getText()), manaCostTextField.getText(), powerTextField.getText(),
                toughnessTextField.getText(), loyaltyTextField.getText(), blackCheckBox.isSelected(),
                greenCheckBox.isSelected(), redCheckBox.isSelected(), blueCheckBox.isSelected(),
                whiteCheckBox.isSelected(), cmc, rarity, format);

        cardsFoundLabel.setText(String.valueOf(cards.size()));
        cards.sort(Comparator.comparing(Card::getName));
        cardsTable.setModel(new DeckBuilderCardTableModel(cards));
        setCursor(null);
    }

    private List<String> getSplit(String str) {
        List<String> split = new ArrayList<>();

        if (str.isEmpty()) {
            return split;
        }

        String[] splited = str.trim().split(" ");

        for (String s : splited) {
            split.add(s.trim());
        }

        return split;
    }

    private void createUIComponents() {
        cardsTable = new DeckBuilderCardTable();
        cardDeckTable = new CardDeckTable();
    }
}
