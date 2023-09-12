package br.com.bertolucci.mtgtools.ui;

import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.infra.DeckBuilderServiceImpl;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.ui.util.Task;
import br.com.bertolucci.mtgtools.ui.util.SwingWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitDialog extends JDialog {

    private JPanel contentPane;
    private JLabel infoLabel;

    private Timer timer;
    private DeckBuilderService deckBuilderService;
    private DownloadTask task;
    private JFrame frame;


    public InitDialog(JFrame frame) {
        this.frame = frame;
        this.setContentPane(contentPane);
        this.setModal(true);
        this.setTitle("Inicializador de serviços");
        this.pack();
        centralize();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        prepareTask();
        this.setVisible(true);
    }

    private void prepareTask() {
        task = new DownloadTask(4);
        timer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                infoLabel.setText(task.getCurrentTask());

                if (task.isDone()) {
                    timer.stop();
                    setCursor(null);
                    dispose();
                }
            }
        });
        timer.start();
        task.go();
    }

    protected void centralize() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    class DownloadTask extends Task {
        private String currentTask;

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
                    currentTask = null;
                    return new ActualTask();
                }
            };
            worker.start();
        }

        @Override
        public void stop() {
            canceled = true;
            currentTask = null;
        }

        public String getCurrentTask() {
            return currentTask;
        }

        class ActualTask {
            ActualTask() {
                initialTask();
                initServicesTask();
                downloadSetImages();
                downloadSymbolImages();
                done = true;
            }

            private void initialTask() {
                currentTask = "Inicializando os serviços";
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                current++;
            }

            private void initServicesTask() {
                currentTask = "Preparando os sets e símbolos";
                try {
                    deckBuilderService = new DeckBuilderServiceImpl();
                    new MainMenu(frame, deckBuilderService);
                } catch (NoApiConnectionException e) {
                    JOptionPane.showMessageDialog(contentPane, "Erro ao conectar a API e atualizar os sets e símbolos",
                            "Gerenciador de downloads", JOptionPane.ERROR_MESSAGE);
                }
                current++;
            }

            private void downloadSetImages() {
                currentTask = "Baixando imagens dos sets.  Isso pode demorar alguns minutos";
                deckBuilderService.downloadSetImages();
                current++;
            }

            private void downloadSymbolImages() {
                currentTask = "Baixando imagens dos símbolos.  Isso pode demorar alguns minutos";
                deckBuilderService.downloadSymbolImages();
                current++;
            }
        }
    }
}
