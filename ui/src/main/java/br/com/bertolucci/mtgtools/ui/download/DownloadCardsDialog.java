package br.com.bertolucci.mtgtools.ui.download;

import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.downloader.exception.NoApiConnectionException;
import br.com.bertolucci.mtgtools.ui.util.Task;
import br.com.bertolucci.mtgtools.ui.util.SwingWorker;
import lombok.SneakyThrows;
import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DownloadCardsDialog extends JDialog {

    private JPanel contentPane;
    private JLabel infoLabel;

    private DownloadTask task;
    private Timer timer;
    private DeckBuilderService deckBuilderService;
    private int counter = 1;

    public DownloadCardsDialog(DeckBuilderService deckBuilderService) throws NoApiConnectionException, InterruptedException {
        this.deckBuilderService = deckBuilderService;

        setup();

        task.go();
        timer.start();
        this.setVisible(true);
    }

    private void centralize() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    private void setup() {
        this.setContentPane(contentPane);
        this.setModal(true);
        this.setTitle("Gerenciador de downloads");
        this.pack();
        centralize();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        initListeners();

        task = new DownloadTask();
    }

    private void initListeners() {

        timer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                infoLabel.setText(WordUtils.capitalize(".".repeat(counter)));

                counter++;
                if (counter == 4) counter = 1;

                if (task.isDone()) {
                    Toolkit.getDefaultToolkit().beep();
                    timer.stop();
                    setCursor(null);

                    JOptionPane.showMessageDialog(
                            contentPane,
                            "Atualização concluída com sucesso",
                            "Gerenciador de downloads",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                if (timer.isRunning()) {
                    JOptionPane.showMessageDialog(
                            contentPane,
                            "A atualização continuará em segundo plano",
                            "Gerenciador de downloads",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
    }

    class DownloadTask extends Task {

        public DownloadTask() {
        }

        @Override
        public void go() {
            final SwingWorker worker = new SwingWorker() {
                @SneakyThrows
                public Object construct() {
                    current = 0;
                    done = false;
                    canceled = false;
                    return new ActualTask();
                }
            };
            worker.start();
        }

        @Override
        public void stop() {
            canceled = true;
        }

        class ActualTask {
            ActualTask() throws NoApiConnectionException, InterruptedException {
                deckBuilderService.updateLegalities();
                done = true;
            }
        }
    }
}
