package br.com.bertolucci.mtgtools.deckbuilder.application.card;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Card;
import br.com.bertolucci.mtgtools.deckbuilder.domain.card.Face;
import br.com.bertolucci.mtgtools.pngsvgtools.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.ImageDownloadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DownloadCardImageService {

    private CollectionService collectionService;
    private DownloadImageService downloadImageService;
    private Card card;
    private Path appDir = Paths.get("app/");
    private Path cardsDir = Paths.get("app/cards/");
    private Path setDir;
    private Path cardPath;
    private boolean hasFaces;

    public DownloadCardImageService(Card card, CollectionService collectionService,
                                    DownloadImageService downloadImageService) {
        this.card = card;
        this.collectionService = collectionService;
        this.downloadImageService = downloadImageService;
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
        List<Face> faces = collectionService.findFacesByCard(card.getId());

        if (!faces.isEmpty() && card.getImageUri() == null) {
            int i = 1;
            hasFaces = true;

            for (Face face: faces) {
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

    public Path getCardPath() {
        return cardPath;
    }

    public boolean hasFaces() {
        return hasFaces;
    }
}
