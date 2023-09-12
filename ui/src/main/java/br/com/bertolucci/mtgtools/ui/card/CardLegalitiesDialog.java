package br.com.bertolucci.mtgtools.ui.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;

import javax.swing.*;
import java.awt.event.*;
import java.util.EnumSet;

public class CardLegalitiesDialog extends AbstractDialog {

    private JPanel contentPane;
    private JButton addLegalitiesButton;
    private JButton cancelButton;
    private JComboBox<Legality> standardComboBox;
    private JComboBox<Legality> alchemyComboBox;
    private JComboBox<Legality> brawlComboBox;
    private JComboBox<Legality> commanderComboBox;
    private JComboBox<Legality> explorerComboBox;
    private JComboBox<Legality> historicComboBox;
    private JComboBox<Legality> legacyComboBox;
    private JComboBox<Legality> modernComboBox;
    private JComboBox<Legality> pioneerComboBox;
    private JComboBox<Legality> pauperComboBox;
    private JComboBox<Legality> oathbreakerComboBox;
    private JComboBox<Legality> vintageComboBox;

    // app
    private Card card;
    private Legalities legalities;

    public CardLegalitiesDialog(Card card, Legalities legalities) {
        this.card = card;
        this.legalities = legalities;

        prepareComboBoxes();
        fill();
        init(contentPane, "Legalidades do card");
    }

    private void prepareComboBox(JComboBox<Legality> comboBox) {
        for (Legality e : EnumSet.allOf(Legality.class)) {
            comboBox.addItem(e);
        }
    }

    private void prepareComboBoxes() {
        prepareComboBox(standardComboBox);
        prepareComboBox(alchemyComboBox);
        prepareComboBox(brawlComboBox);
        prepareComboBox(commanderComboBox);
        prepareComboBox(explorerComboBox);
        prepareComboBox(historicComboBox);
        prepareComboBox(legacyComboBox);
        prepareComboBox(modernComboBox);
        prepareComboBox(pioneerComboBox);
        prepareComboBox(pauperComboBox);
        prepareComboBox(oathbreakerComboBox);
        prepareComboBox(vintageComboBox);
    }

    protected void initListeners() {
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addLegalities();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        addLegalitiesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                addLegalities();
            }
        });
    }

    private void addLegalities() {
        card.setLegalities(build());
        dispose();
    }

    private void fill() {
        standardComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getStandard() != null
                ? card.getLegalities().getStandard()
                : standardComboBox.getItemAt(0));

        alchemyComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getAlchemy() != null
                ? card.getLegalities().getAlchemy()
                : alchemyComboBox.getItemAt(0));

        brawlComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getBrawl() != null
                ? card.getLegalities().getBrawl()
                : brawlComboBox.getItemAt(0));

        commanderComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getCommander() != null
                ? card.getLegalities().getCommander()
                : commanderComboBox.getItemAt(0));

        explorerComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getExplorer() != null
                ? card.getLegalities().getExplorer()
                : explorerComboBox.getItemAt(0));

        historicComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getHistoric() != null
                ? card.getLegalities().getHistoric()
                : historicComboBox.getItemAt(0));

        legacyComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getLegacy() != null
                ? card.getLegalities().getLegacy()
                : legacyComboBox.getItemAt(0));

        modernComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getModern() != null
                ? card.getLegalities().getModern()
                : modernComboBox.getItemAt(0));

        pioneerComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getPioneer() != null
                ? card.getLegalities().getPioneer()
                : pioneerComboBox.getItemAt(0));

        pauperComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getPauper() != null
                ? card.getLegalities().getPauper()
                : pauperComboBox.getItemAt(0));

        oathbreakerComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getOathBreaker() != null
                ? card.getLegalities().getOathBreaker()
                : oathbreakerComboBox.getItemAt(0));

        vintageComboBox.setSelectedItem(card.getLegalities() != null && card.getLegalities().getVintage() != null
                ? card.getLegalities().getVintage()
                : vintageComboBox.getItemAt(0));
    }

    private Legalities build() {
        legalities.setStandard((Legality) standardComboBox.getSelectedItem());
        legalities.setAlchemy((Legality) alchemyComboBox.getSelectedItem());
        legalities.setBrawl((Legality) brawlComboBox.getSelectedItem());
        legalities.setCommander((Legality) commanderComboBox.getSelectedItem());
        legalities.setExplorer((Legality) explorerComboBox.getSelectedItem());
        legalities.setHistoric((Legality) historicComboBox.getSelectedItem());
        legalities.setLegacy((Legality) legacyComboBox.getSelectedItem());
        legalities.setModern((Legality) modernComboBox.getSelectedItem());
        legalities.setPioneer((Legality) pioneerComboBox.getSelectedItem());
        legalities.setPauper((Legality) pauperComboBox.getSelectedItem());
        legalities.setOathBreaker((Legality) oathbreakerComboBox.getSelectedItem());
        legalities.setVintage((Legality) vintageComboBox.getSelectedItem());
        return legalities;
    }
}
