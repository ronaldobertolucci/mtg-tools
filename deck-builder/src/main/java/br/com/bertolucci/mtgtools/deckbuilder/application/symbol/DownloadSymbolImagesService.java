package br.com.bertolucci.mtgtools.deckbuilder.application.symbol;

import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.converter.DownloadSvgAndConvertToPngService;
import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DownloadSymbolImagesService {

    private DownloadImageService downloadImageService;
    private DownloadSvgAndConvertToPngService downloadSvgAndConvertToPngService;
    private Path imagesPath = Paths.get("app/");
    private Path symbolsPath = Paths.get("app/symbols/");


    public DownloadSymbolImagesService(DownloadImageService downloadImageService) {
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

        if (!Files.exists(symbolsPath)) {
            Files.createDirectory(symbolsPath);
        }
    }

    public void init(List<Symbol> symbols) {
        symbols.forEach(symbol -> {
            Path symbolImgPath = Paths.get(symbolsPath.toString(), symbol.getSymbol().replace("/", "") + "16.png");
            try {
                if (!Files.exists(symbolImgPath)) {
                    downloadSvgAndConvertToPngService = new DownloadSvgAndConvertToPngService(
                            symbol.getImageUri(), symbolImgPath, downloadImageService);
                    downloadSvgAndConvertToPngService.setSize(new Dimension(16, 16));
                    downloadSvgAndConvertToPngService.convert();
                }
            } catch (ImageDownloadException ignored) {
            }
        });
    }
}
