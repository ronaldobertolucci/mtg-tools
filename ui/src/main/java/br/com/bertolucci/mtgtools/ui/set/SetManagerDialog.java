package br.com.bertolucci.mtgtools.ui.set;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;
import br.com.bertolucci.mtgtools.ui.card.*;
import br.com.bertolucci.mtgtools.ui.util.FontUtil;
import br.com.bertolucci.mtgtools.ui.util.OptionDialogUtil;
import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SetManagerDialog extends AbstractDialog {
    private JPanel contentPane;
    private JButton downloadCardsButton;
    private JComboBox setListComboBox;
    private JLabel setNameLabel;
    private JLabel setTypeLabel;
    private JLabel setReleaseDateLabel;
    private JLabel setTotalCardsLabel;
    private JLabel setImage;
    private List<JLabel> sidebarInfo = new ArrayList<>();
    private JButton addCardButton;
    private JTable cardsTable;


    // app
    private DeckBuilderService deckBuilderService;
    private Set selectedSet;
    private List<Set> sets;

    public SetManagerDialog(DeckBuilderService deckBuilderService) {
        this.deckBuilderService = deckBuilderService;
        this.sets = deckBuilderService.getSets();

        setup();
        init(contentPane, "Meus cards");
    }

    private void setup() {
        prepareSideBar();
        prepareComboBox();
        selectSet();
    }

    private void prepareSideBar() {
        sidebarInfo.add(setNameLabel);
        sidebarInfo.add(setTypeLabel);
        sidebarInfo.add(setReleaseDateLabel);
        sidebarInfo.add(setTotalCardsLabel);
        sidebarInfo.add(setImage);
        FontUtil.setFont(sidebarInfo, FontUtil.getFont("fonts/Planewalker Bold.otf", 20F));
    }

    private void prepareComboBox() {
        sets.forEach(set -> setListComboBox.addItem(set));
    }

    protected void initListeners() {
        setListComboBox.addActionListener((actionEvent) -> {
            selectSet();
        });

        downloadCardsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                downloadCardsBySet(selectedSet);
            }
        });

        addCardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                insertCard();
            }
        });

        cardsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Card card;
                switch (cardsTable.columnAtPoint(e.getPoint())) {
                    case 5:
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                        card = (Card) cardsTable.getModel().getValueAt(cardsTable.rowAtPoint(e.getPoint()), 5);
                        new CardDetailDialog(card, deckBuilderService);

                        setCursor(null);

                        break;
                    case 6:
                        card = (Card) cardsTable.getModel().getValueAt(cardsTable.rowAtPoint(e.getPoint()), 6);
                        updateCard(card);
                        break;
                    case 7:
                        card = (Card) cardsTable.getModel().getValueAt(cardsTable.rowAtPoint(e.getPoint()), 7);
                        removeCard(card);
                        break;
                }
            }
        });
    }

    private void selectSet() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        selectedSet = (Set) setListComboBox.getSelectedItem();

        ImageIcon imageIcon = null;
        try {
            if (!Files.exists(Paths.get("app/sets/" + selectedSet.getCode() + "250_666.png"))) {
                throw new Exception();
            }

            imageIcon = new ImageIcon("app/sets/" + selectedSet.getCode() + "250_666.png");
        } catch (Exception e) {
            imageIcon = new ImageIcon(Objects.requireNonNull(this.getClass()
                    .getClassLoader()
                    .getResource("images/no-image96.png")));
        }

        updateSetInformation(
                WordUtils.capitalize(selectedSet.getName()),
                WordUtils.capitalize(selectedSet.getType().getTranslatedName()),
                selectedSet.getReleasedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.valueOf(selectedSet.getTotalCards()),
                imageIcon
        );

        loadCards(selectedSet.getId());
        setCursor(null);
    }

    private void updateSetInformation(String setName, String setType, String releasedAt,
                                      String totalCards, ImageIcon setImageIcon) {
        setNameLabel.setText(setName);
        setTypeLabel.setText(setType);
        setReleaseDateLabel.setText(releasedAt);
        setTotalCardsLabel.setText(totalCards);
        setImage.setIcon(setImageIcon);
    }

    private void loadCards(int setId) {
        List<Card> cards = deckBuilderService.getCardsBySet(setId);
        cards.sort(Comparator.comparing(Card::getName));
        cardsTable.setModel(new CardTableModel(cards));
    }

    private void downloadCardsBySet(Set set) {
        if (OptionDialogUtil.showDialog(this, "Deseja importar os cards?\nIsso pode demorar alguns segundos.") != 0) {
            return;
        }

        download(set);
        loadCards(set.getId());
    }

    private void download(Set set) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            deckBuilderService.importCardsBySet(set.getCode());
        } catch (NoApiConnectionException e) {
            JOptionPane.showMessageDialog(this, "Sem conexão com a API", "Um erro ocorreu", JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(null);
        }
    }

    private void insertCard() {
        new InsertCardDialog(selectedSet, deckBuilderService);
        loadCards(selectedSet.getId());
    }

    private void updateCard(Card card) {
        new UpdateCardDialog(selectedSet, deckBuilderService, card);
        loadCards(selectedSet.getId());
    }

    private void removeCard(Card card) {
        if (OptionDialogUtil.showDialog(this,
                "Deseja excluir o card " + WordUtils.capitalize(card.getName()) + "?") != 0) {
            return;
        }

        deckBuilderService.removeCard(card);
        loadCards(card.getSet().getId());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        cardsTable = new CardTable();
    }
}
