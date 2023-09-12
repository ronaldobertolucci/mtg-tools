package br.com.bertolucci.mtgtools.ui.ruling;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Ruling;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RulingDialog extends AbstractDialog {
    protected JPanel contentPane;
    protected JButton addRulingButton;
    protected JButton cancelButton;
    protected JTextField rulingSourceTextField;
    protected JTextField rulingPublishedAtTextField;
    protected JTextArea rulingCommentTextArea;

    //app
    protected Card card;
    protected Ruling ruling;
    protected DeckBuilderService deckBuilderService;

    public RulingDialog(Card card, DeckBuilderService deckBuilderService, Ruling ruling) {
        this.card = card;
        this.deckBuilderService = deckBuilderService;
        this.ruling = ruling;
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

    protected Ruling build() {
        LocalDate publishedAt = LocalDate.parse(
                rulingPublishedAtTextField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        ruling.setCard(card);
        ruling.setComment(rulingCommentTextArea.getText());
        ruling.setSource(rulingSourceTextField.getText());
        ruling.setPublishedAt(publishedAt.toString());
        return ruling;
    }

}