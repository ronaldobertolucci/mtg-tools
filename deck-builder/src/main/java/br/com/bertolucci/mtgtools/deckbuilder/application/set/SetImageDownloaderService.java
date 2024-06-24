package br.com.bertolucci.mtgtools.deckbuilder.application.set;

import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.pngsvgtools.converter.DownloadSvgAndConvertToPngService;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageServiceImpl;
import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SetImageDownloaderService implements Runnable {

    private int start;
    private int end;
    private List<Set> sets;
    private DownloadImageService downloadImageService = new DownloadImageServiceImpl();

    private DownloadSvgAndConvertToPngService downloadSvgAndConvertToPngService;
    private Path imagesPath = Paths.get("app/");
    private Path setsPath = Paths.get("app/sets/");

    public SetImageDownloaderService(int start, int end, List<Set> sets) {
        this.start = start;
        this.end = end;
        this.sets = sets;

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

    @SneakyThrows({ImageDownloadException.class})
    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            int width = 250;
            Path setImgPath = Paths.get(setsPath.toString(), sets.get(i).getCode() + width + ".png");

            if (!Files.exists(setImgPath)) {
                System.out.println("INFO: Procurando imagens de set na API Scryfall -> Set: " + sets.get(i).getName());
                downloadSvgAndConvertToPngService = new DownloadSvgAndConvertToPngService(
                        sets.get(i).getImageUri(), setImgPath, downloadImageService);
                downloadSvgAndConvertToPngService.setSize(new Dimension(width, width));
                downloadSvgAndConvertToPngService.convert();
            }
        }
    }

}
