package br.com.bertolucci.mtgtools.ui.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.*;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;
import br.com.bertolucci.mtgtools.ui.face.FaceListDialog;
import br.com.bertolucci.mtgtools.ui.part.PartListDialog;
import br.com.bertolucci.mtgtools.ui.ruling.RulingListDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumSet;
import java.util.Objects;

public class CardDialog extends AbstractDialog {

    protected JPanel contentPane;
    protected JTextField cardNameTextField;
    protected JComboBox cardLanguageComboBox;
    protected JTextField cardPowerTextField;
    protected JTextField cardToughnessTextField;
    protected JTextField cardTypeLineTextField;
    protected JComboBox cardLayoutComboBox;
    protected JTextField cardLoyaltyTextField;
    protected JTextField cardManaCostTextField;
    protected JTextArea cardOracleTextTextArea;
    protected JComboBox cardImageStatusComboBox;
    protected JComboBox cardBorderColorComboBox;
    protected JTextArea cardFlavorTextTextArea;
    protected JTextField cardCollectorNumberTextField;
    protected JCheckBox cardDigitalCheckBox;
    protected JComboBox cardRarityComboBox;
    protected JTextField cardOracleIdTextField;
    protected JButton cancelCardButton;
    protected JButton saveCardButton;
    protected JButton faceButton;
    protected JButton partButton;
    protected JButton rulingButton;
    protected JButton legalityButton;
    protected JTextField cardImagePathTextField;
    protected JLabel infoLabel;
    protected JTextField cardCmcTextField;
    protected JLabel infoCmcLabel;
    private JLabel infoImageLabel;

    protected Set selectedSet;
    protected DeckBuilderService deckBuilderService;
    protected Card card;

    public CardDialog(Set selectedSet, DeckBuilderService deckBuilderService, Card card) {
        this.selectedSet = selectedSet;
        this.deckBuilderService = deckBuilderService;
        this.card = card;

        prepareComboBoxes();
    }

    private <E extends Enum> void prepareComboBox(Class<E> var, JComboBox<E> comboBox) {
        for (Object e : EnumSet.allOf(var)) {
            comboBox.addItem((E) e);
        }
    }

    protected void prepareComboBoxes() {
        prepareComboBox(Lang.class, cardLanguageComboBox);
        prepareComboBox(Layout.class, cardLayoutComboBox);
        prepareComboBox(BorderColor.class, cardBorderColorComboBox);
        prepareComboBox(ImageStatus.class, cardImageStatusComboBox);
        prepareComboBox(Rarity.class, cardRarityComboBox);
    }

    protected void initListeners() {
        cancelCardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });

        infoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(contentPane, """
                                Os símbolos de mana devem ser separados por chaves, como {U}, por exemplo.
                                Para correta renderização, cada símbolo deve seguir o padrão abaixo:
                                
                                {U}: azul
                                {W}: branco
                                {B}: preto
                                {G}: verde
                                {R}: vermelho
                                {C}: sem cor
                                {P}: uma mana colorida ou dois pontos de vida
                                {S}: mana de gelo (snow)
                                
                                Números seguem o mesma ideia, ou seja, {1}, {7}, ..., {20}.
                                
                                Símbolos divididos devem ser escritos separados por barra, como {R/G}. Eles devem seguir
                                a ordem em que aparecem na imagem original. {G/R}, por exemplo, não produzirá resultado,
                                visto que a ordem correta é {R/G}.
                                
                                Símbolos triplos, como {U/R/P}, {R/G/P} etc, são usados para representar símbolos duplos
                                que envolvem mana colorida ou dois pontos de vida. Neste caso, o P é sempre colocado ao
                                final.
                                
                                Todos os símbolos presentes neste programa podem ser consultados em Coleção/Símbolos.
                                """,
                        "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoCmcLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(contentPane, """
                                O CMC (do inglês Converted Mana Cost) é a soma dos custos de mana, desconsiderando suas
                                cores.
                                
                                Um card {2}{W}{W}, por exemplo, tem cmc igual a 4.
                                """,
                        "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        infoImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(contentPane, """
                                Este campo deve ser usado para informar o caminho da imagem a ser utilizada na impressão
                                do deck.
                                
                                Podem ser fornecidos caminhos (URLs) da web ou locais. Para o correto funcionamento,
                                entretanto, usuários de Windows devem informar o caminho (local) começando por
                                'file:///<disco>/<caminho>'. Vejamos um exemplo:
                                
                                file:///C:/Users/ronal/Downloads/card.png
                                
                                Para melhores resultados, recomenda-se que as imagens estejam em formato PNG com tamanho
                                745 × 1040.
                                """,
                        "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        partButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadParts();
            }
        });

        rulingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadRulings();
            }
        });

        legalityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadLegalities();
            }
        });

        faceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadFaces();
            }
        });
    }

    protected void loadFaces() {
        new FaceListDialog(card, deckBuilderService);
    }

    protected void loadParts() {
        new PartListDialog(card, deckBuilderService);
    }

    protected void loadRulings() {
        new RulingListDialog(card, deckBuilderService);
    }

    protected void loadLegalities() {
        Legalities legalities = card.getLegalities() != null ? card.getLegalities() : new Legalities();
        new CardLegalitiesDialog(card, legalities);
    }

    protected Card build() {
        Lang lang = (Lang) Objects.requireNonNull(cardLanguageComboBox.getSelectedItem());
        Layout layout = (Layout) Objects.requireNonNull(cardLayoutComboBox.getSelectedItem());
        ImageStatus imageStatus = (ImageStatus) Objects.requireNonNull(cardImageStatusComboBox.getSelectedItem());
        BorderColor borderColor = (BorderColor) Objects.requireNonNull(cardBorderColorComboBox.getSelectedItem());
        Rarity rarity = (Rarity) Objects.requireNonNull(cardRarityComboBox.getSelectedItem());

        card.setSet(selectedSet);
        card.setName(cardNameTextField.getText());
        card.setLang(lang.name().toUpperCase());
        card.setLayout(layout.name().toUpperCase());
        card.setImageStatus(imageStatus.name().toUpperCase());
        card.setBorderColor(borderColor.name().toUpperCase());
        card.setRarity(rarity.name().toUpperCase());
        card.setCollectorNumber(cardCollectorNumberTextField.getText());
        card.setIsDigital(cardDigitalCheckBox.isSelected());
        card.setOracleText(cardOracleTextTextArea.getText());
        card.setPower(cardPowerTextField.getText());
        card.setToughness(cardToughnessTextField.getText());
        card.setLoyalty(cardLoyaltyTextField.getText());
        card.setTypeLine(cardTypeLineTextField.getText());
        card.setManaCost(cardManaCostTextField.getText());
        card.setCmc(Double.valueOf(cardCmcTextField.getText().isEmpty() ? "0.0" : cardCmcTextField.getText()));
        card.setOracleId(cardOracleIdTextField.getText());
        card.setFlavorText(cardFlavorTextTextArea.getText());
        card.setImageUri(cardImagePathTextField.getText());
        return card;
    }
}
