package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageServiceImpl;
import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CardImageDownloaderService {

    private DownloadImageService downloadImageService = new DownloadImageServiceImpl();
    private Card card;
    private Path appDir = Paths.get("app/");
    private Path cardsDir = Paths.get("app/cards/");
    private Path setDir;
    @Getter
    private Path cardPath;

    public CardImageDownloaderService(Card card) {
        this.card = card;
        this.setDir = Paths.get("app/cards/" + card.getSet().getId() + "/");
        this.cardPath = Paths.get(setDir + "/" + card.getId() + ".png");

        try {
            createDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDirectory() throws IOException {
        if (!Files.exists(appDir)) {
            Files.createDirectory(appDir);
        }

        if (!Files.exists(cardsDir)) {
            Files.createDirectory(cardsDir);
        }

        if (!Files.exists(setDir)) {
            Files.createDirectory(setDir);
        }
    }

    private void download(String uri, Path path) {
        try {
            downloadImageService.downloadPng(uri, path.toString());
        } catch (ImageDownloadException ignored) {
        }
    }

    public void init() {
        if (!card.getFaces().isEmpty() && card.getImageUri() == null) {
            int i = 1;

            for (Face face: card.getFaces()) {
                if (!Files.exists(Paths.get(cardPath.toString().replace(".png", "") + "_" + i + ".png"))) {
                    download(face.getImageUri(), Paths.get(cardPath.toString().replace(".png", "") + "_" + i + ".png"));
                }

                i++;
            }
            return;
        }

        if (!Files.exists(cardPath)) {
            download(card.getImageUri(), cardPath);
        }
    }
}