package br.com.bertolucci.mtgtools.ui.deck;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.application.deck.CheckLegalitiesService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.CardRarity;
import br.com.bertolucci.mtgtools.deckbuilder.domain.carddeck.CardDeck;
import br.com.bertolucci.mtgtools.deckbuilder.domain.deck.Deck;
import br.com.bertolucci.mtgtools.shared.card.CardSearchParametersDto;
import br.com.bertolucci.mtgtools.shared.util.SplitColorSqlOperator;
import br.com.bertolucci.mtgtools.shared.util.SplitStringSqlOperator;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;
import br.com.bertolucci.mtgtools.ui.card.CardDetailDialog;
import br.com.bertolucci.mtgtools.ui.carddeck.*;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    private JTextField cmcTextField;
    private JComboBox rarityComboBox;
    private JLabel infoManaCostLabel;
    private JCheckBox highQualityCheckBox;
    private JLabel cardsFoundLabel;
    private JLabel infoColorLabel;
    private JLabel infoCmcLabel;
    private JTextField colorTextField;
    private JTable sideboardCardsTable;
    private JLabel totalSideboardCardsLabel;

    private DeckBuilderService deckBuilderService;
    private Deck deck;

    public DeckBuilderDialog(DeckBuilderService deckBuilderService, Deck deck) {
        this.deckBuilderService = deckBuilderService;
        this.deck = deck;

        checkCards(deckBuilderService, deck);
        prepareRarityComboBox();
        load();
        init(contentPane, "Construir deck");
    }

    private void checkCards(DeckBuilderService deckBuilderService, Deck deck) {
        CheckLegalitiesService checkLegalitiesService = new CheckLegalitiesService(deck, deckBuilderService);
        if (checkLegalitiesService.hasIllegal()) {
            if (OptionDialogUtil.showDialog(
                    this,
                    "O deck possui cards não legais neste formato. Deseja removê-los?") == 0) {
                checkLegalitiesService.removeIllegalCards();
            }
        }
    }

    private void prepareRarityComboBox() {
        rarityComboBox.addItem("Selecione uma raridade");
        for (CardRarity rarity : EnumSet.allOf(CardRarity.class)) {
            rarityComboBox.addItem(rarity);
        }
        rarityComboBox.setPrototypeDisplayValue("");
    }

    private void load() {
        AtomicInteger totalCards = new AtomicInteger();
        List<CardDeck> deckCards = new java.util.ArrayList<>(
            deck.getCards().stream().filter(cd -> !cd.getIsSideboard()).toList()
        );
        deckCards.forEach(cardDeck -> {
            totalCards.addAndGet(cardDeck.getQuantity());
        });
        deckCards.sort(Comparator.comparing(cardDeck -> cardDeck.getCard().getName()));
        totalCardsLabel.setText(String.valueOf(totalCards));

        AtomicInteger totalSideboardCards = new AtomicInteger();
        List<CardDeck> sideboardCards = new java.util.ArrayList<>(
            deck.getCards().stream().filter(CardDeck::getIsSideboard).toList()
        );
        sideboardCards.forEach(cardDeck -> {
            totalSideboardCards.addAndGet(cardDeck.getQuantity());
        });
        sideboardCards.sort(Comparator.comparing(cardDeck -> cardDeck.getCard().getName()));
        totalSideboardCardsLabel.setText(String.valueOf(totalSideboardCards));

        deckNameLabel.setText(WordUtils.capitalize(deck.getName()));
        formatLabel.setText(WordUtils.capitalize(deck.getDeckFormat().getTranslatedName()));
        cardDeckTable.setModel(new CardDeckTableModel(deckCards));
        sideboardCardsTable.setModel(new CardDeckTableModel(sideboardCards));
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
                    case 2:
                        cardDeck = (CardDeck) cardDeckTable.getModel().getValueAt(cardDeckTable.rowAtPoint(e.getPoint()), 2);
                        update(cardDeck);
                        break;
                    case 3:
                        cardDeck = (CardDeck) cardDeckTable.getModel().getValueAt(cardDeckTable.rowAtPoint(e.getPoint()), 3);
                        removeCardDeck(cardDeck);
                }
            }
        });

        sideboardCardsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CardDeck cardDeck;
                switch (sideboardCardsTable.columnAtPoint(e.getPoint())) {
                    case 2:
                        cardDeck = (CardDeck) sideboardCardsTable.getModel().getValueAt(sideboardCardsTable.rowAtPoint(e.getPoint()), 2);
                        update(cardDeck);
                        break;
                    case 3:
                        cardDeck = (CardDeck) sideboardCardsTable.getModel().getValueAt(sideboardCardsTable.rowAtPoint(e.getPoint()), 3);
                        removeCardDeck(cardDeck);
                }
            }
        });

        infoTypeLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                URL url = Resources.getResource("texts/type_search.txt");
                String text = Resources.toString(url, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(contentPane, text,"Busca de tipos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoCmcLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                URL url = Resources.getResource("texts/cmc.txt");
                String text = Resources.toString(url, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(contentPane, text,"Busca de tipos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoColorLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                URL url = Resources.getResource("texts/color_search.txt");
                String text = Resources.toString(url, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(contentPane, text,"Busca de cores", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoOracleTextLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                URL url = Resources.getResource("texts/oracle_search.txt");
                String text = Resources.toString(url, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(contentPane, text,"Busca de texto", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoManaCostLabel.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                URL url = Resources.getResource("texts/mana_cost_search.txt");
                String text = Resources.toString(url, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(contentPane, text,"Busca de palavras-chave",
                        JOptionPane.INFORMATION_MESSAGE);
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

        deckBuilderService.removeCardFromDeck(cardDeck);
        load();
    }

    private void clean() {
        highQualityCheckBox.setSelected(true);

        nameTextField.setText(null);
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

        List<Card> cards = deckBuilderService.findCardsByParameter(new CardSearchParametersDto(
                nameTextField.getText(),
                SplitStringSqlOperator.split(typeTextField.getText()),
                manaCostTextField.getText(),
                powerTextField.getText(),
                toughnessTextField.getText(),
                loyaltyTextField.getText(),
                SplitStringSqlOperator.split(oracleTextTextField.getText()),
                SplitColorSqlOperator.split(colorTextField.getText()),
                getCmc(),
                getRarity(),
                deck.getDeckFormat().name().toLowerCase(),
                highQualityCheckBox.isSelected()
        ), 500);

        cardsFoundLabel.setText(String.valueOf(cards.size()));
        cards.sort(Comparator.comparing(Card::getName));
        cardsTable.setModel(new DeckBuilderCardTableModel(cards));
        setCursor(null);
    }

    private String getRarity() {
        CardRarity rarity = rarityComboBox.getSelectedIndex() != 0
                ? (CardRarity) rarityComboBox.getSelectedItem()
                : null;

        return rarity == null ? null : rarity.name();
    }

    private Double getCmc() {
        Double cmc = null;
        try {
            cmc = Double.valueOf(cmcTextField.getText());
        } catch (NumberFormatException ignored) {
        }

        return cmc;
    }

    private void createUIComponents() {
        cardsTable = new DeckBuilderCardTable();
        cardDeckTable = new CardDeckTable();
        sideboardCardsTable = new CardDeckTable();
    }
}
