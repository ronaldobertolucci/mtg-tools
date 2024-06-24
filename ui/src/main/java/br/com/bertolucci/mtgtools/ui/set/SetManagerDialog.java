package br.com.bertolucci.mtgtools.ui.set;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;
import br.com.bertolucci.mtgtools.ui.card.CardDialog;
import br.com.bertolucci.mtgtools.ui.card.CardTable;
import br.com.bertolucci.mtgtools.ui.card.CardTableModel;
import br.com.bertolucci.mtgtools.ui.download.DownloadCardsDialog;
import br.com.bertolucci.mtgtools.ui.util.FontUtil;
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
    private JComboBox setListComboBox;
    private JLabel setNameLabel;
    private JLabel setTypeLabel;
    private JLabel setReleaseDateLabel;
    private JLabel setTotalCardsLabel;
    private JLabel setImage;
    private List<JLabel> sidebarInfo = new ArrayList<>();
    private JTable cardsTable;
    private JLabel lastUpdateLabel;
    private JButton updateButton;


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
        prepareLastUpdate();

        sidebarInfo.add(setNameLabel);
        sidebarInfo.add(setTypeLabel);
        sidebarInfo.add(setReleaseDateLabel);
        sidebarInfo.add(setTotalCardsLabel);
        sidebarInfo.add(setImage);
        FontUtil.setFont(sidebarInfo, FontUtil.getFont("fonts/Planewalker Bold.otf", 20F));
    }

    private void prepareLastUpdate() {
        String lastUpdate = deckBuilderService.lastUpdate() == null
                ? "Nunca atualizado"
                : deckBuilderService.lastUpdate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        lastUpdateLabel.setText(lastUpdate);
    }

    private void prepareComboBox() {
        sets.forEach(set -> setListComboBox.addItem(set));
    }

    protected void initListeners() {
        setListComboBox.addActionListener((actionEvent) -> {
            selectSet();
        });

        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    new DownloadCardsDialog(deckBuilderService);
                } catch (NoApiConnectionException | InterruptedException exception) {
                    JOptionPane.showMessageDialog(
                            contentPane,
                            "Erro ao conectar a API e atualizar os cards",
                            "Gerenciador de downloads",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                loadCards(selectedSet.getId());
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
                        new CardDialog(card);
                        setCursor(null);
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
            if (!Files.exists(Paths.get("app/sets/" + selectedSet.getCode() + "250.png"))) {
                throw new Exception();
            }

            imageIcon = new ImageIcon("app/sets/" + selectedSet.getCode() + "250.png");
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
        prepareLastUpdate();
        List<Card> cards = deckBuilderService.findCardsBySet(setId);
        cards.sort(Comparator.comparing(Card::getName));
        cardsTable.setModel(new CardTableModel(cards));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        cardsTable = new CardTable();
    }
}
