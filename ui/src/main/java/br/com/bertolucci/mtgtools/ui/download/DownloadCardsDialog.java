package br.com.bertolucci.mtgtools.ui.download;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.ui.util.Task;
import br.com.bertolucci.mtgtools.ui.util.SwingWorker;
import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DownloadCardsDialog extends JDialog {

    private JPanel contentPane;
    private JLabel infoLabel;
    private JLabel totalCardsLabel;
    private JProgressBar progressBar;

    private List<Set> sets;
    private DeckBuilderService deckBuilderService;
    private DownloadTask task;
    private Timer timer;

    public DownloadCardsDialog(DeckBuilderService deckBuilderService) {
        this.deckBuilderService = deckBuilderService;
        this.sets = deckBuilderService.getSets();
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

        task = new DownloadTask(sets.size());
        prepareProgressBar();
    }

    private void prepareProgressBar() {
        progressBar.setMinimum(0);
        progressBar.setMaximum(task.getLengthOfTask());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
    }

    private void initListeners() {

        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue(task.getCurrent());
                infoLabel.setText(WordUtils.capitalize(task.getSetName()));
                totalCardsLabel.setText(String.valueOf(task.getTotalCards()));

                if (task.isDone()) {
                    Toolkit.getDefaultToolkit().beep();
                    timer.stop();
                    setCursor(null);

                    JOptionPane.showMessageDialog(contentPane, "Download concluído com sucesso",
                            "Gerenciador de downloads", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ev) {
                if (timer.isRunning()) {
                    JOptionPane.showMessageDialog(contentPane, "O download continuará em segundo plano",
                            "Gerenciador de downloads", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    class DownloadTask extends Task {
        private Set actualSet;
        private Integer totalCards;

        public DownloadTask(int lengthOfTask) {
            this.lengthOfTask = lengthOfTask;
        }

        @Override
        public void go() {
            final SwingWorker worker = new SwingWorker() {
                public Object construct() {
                    current = 0;
                    done = false;
                    canceled = false;
                    actualSet = null;
                    totalCards = 0;
                    return new ActualTask();
                }
            };
            worker.start();
        }

        @Override
        public void stop() {
            canceled = true;
            actualSet = null;
            totalCards = 0;
        }

        public String getSetName() {
            if (actualSet != null) {
                return actualSet.getName();
            }

            return null;
        }

        public Integer getTotalCards() {
            return totalCards;
        }

        class ActualTask {
            ActualTask() {
                for (Set set : sets) {
                    actualSet = set;
                    Integer dbTotalCards = deckBuilderService.getTotalCardsBySet(set.getId());

                    try {
                        deckBuilderService.importCardsBySet(set.getCode());
                    } catch (NoApiConnectionException e) {
                        JOptionPane.showMessageDialog(contentPane, "Erro ao conectar a API",
                                "Gerenciador de downloads", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        break;
                    }

                    totalCards += deckBuilderService.getTotalCardsBySet(set.getId()) - dbTotalCards;
                    current++;

                    if (current >= lengthOfTask) {
                        done = true;
                        current = lengthOfTask;
                    }
                }
            }
        }
    }
}
