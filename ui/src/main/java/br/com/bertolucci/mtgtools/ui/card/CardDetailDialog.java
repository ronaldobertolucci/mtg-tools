package br.com.bertolucci.mtgtools.ui.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.application.card.DownloadCardImageService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.ui.AbstractDialog;
import br.com.bertolucci.mtgtools.ui.util.SwingWorker;
import br.com.bertolucci.mtgtools.ui.util.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class CardDetailDialog extends AbstractDialog {
    private JPanel contentPane;
    private JButton cancelButton;
    private JLabel imageLabel1;
    private JLabel imageLabel2;
    private Card card;
    private DeckBuilderService deckBuilderService;

    public CardDetailDialog(Card card, DeckBuilderService deckBuilderService) {
        this.card = card;
        this.deckBuilderService = deckBuilderService;

        download();
        init(contentPane, "Detalhes do card");
    }

    private void download() {
        DownloadCardImageService dcis = deckBuilderService.downloadCardImage(card);

        if (dcis.hasFaces()) {
            prepareImage(imageLabel1, Paths.get(dcis.getCardPath().toString().replace(".png", "") + "_1.png"));
            prepareImage(imageLabel2, Paths.get(dcis.getCardPath().toString().replace(".png", "") + "_2.png"));
            return;
        }

        prepareImage(imageLabel1, dcis.getCardPath());
    }

    private void prepareImage(JLabel label, Path imagePath) {
        if (!Files.exists(imagePath)) {
            label.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass()
                    .getClassLoader()
                    .getResource("images/card-back.png"))));
            return;
        }

        ImageIcon icon = new ImageIcon(imagePath.toString());
        icon.setImage(icon.getImage().getScaledInstance(488, 680, Image.SCALE_SMOOTH));
        label.setIcon(icon);
    }

    @Override
    protected void initListeners() {
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
            }
        });
    }
}
