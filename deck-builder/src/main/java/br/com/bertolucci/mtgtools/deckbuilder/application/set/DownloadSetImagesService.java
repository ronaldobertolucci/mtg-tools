package br.com.bertolucci.mtgtools.deckbuilder.application.set;

import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.pngsvgtools.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.DownloadSvgAndConvertToPngService;
import br.com.bertolucci.mtgtools.pngsvgtools.ImageDownloadException;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DownloadSetImagesService {

    private DownloadImageService downloadImageService;
    private DownloadSvgAndConvertToPngService downloadSvgAndConvertToPngService;
    private Path imagesPath = Paths.get("app/");
    private Path setsPath = Paths.get("app/sets/");


    public DownloadSetImagesService(DownloadImageService downloadImageService) {
        this.downloadImageService = downloadImageService;

        try {
            createDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDirectory() throws IOException {
        if (!Files.exists(imagesPath)) {
            Files.createDirectory(imagesPath);
        }

        if (!Files.exists(setsPath)) {
            Files.createDirectory(setsPath);
        }
    }

    public void init(List<Set> sets) {
        sets.forEach(set -> {
            Path setImgPath = Paths.get(setsPath.toString(), set.getCode() + "250_666.png");
            try {
                if (!Files.exists(setImgPath)) {
                    downloadSvgAndConvertToPngService = new DownloadSvgAndConvertToPngService(
                            set.getImageUri(), setImgPath, downloadImageService);
                    downloadSvgAndConvertToPngService.setColor("#666");
                    downloadSvgAndConvertToPngService.setSize(new Dimension(250, 250));
                    downloadSvgAndConvertToPngService.convert();
                }
            } catch (ImageDownloadException ignored) {
            }
        });
    }
}
