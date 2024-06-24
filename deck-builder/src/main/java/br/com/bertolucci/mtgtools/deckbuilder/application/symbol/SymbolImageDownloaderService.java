package br.com.bertolucci.mtgtools.deckbuilder.application.symbol;

import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
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

public class SymbolImageDownloaderService implements Runnable {

    private int start;
    private int end;
    private List<Symbol> symbols;
    private DownloadImageService downloadImageService = new DownloadImageServiceImpl();

    private DownloadSvgAndConvertToPngService downloadSvgAndConvertToPngService;
    private Path imagesPath = Paths.get("app/");
    private Path symbolsPath = Paths.get("app/symbols/");

    public SymbolImageDownloaderService(int start, int end, List<Symbol> symbols) {
        this.start = start;
        this.end = end;
        this.symbols = symbols;

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

    @SneakyThrows({ImageDownloadException.class})
    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            int width = 16;
            Path symbolImgPath = Paths.get(symbolsPath.toString(),
                    symbols.get(i).getSymbol().replace("/", "") + width + ".png");

            if (!Files.exists(symbolImgPath)) {
                System.out.println("INFO: Procurando imagens de símbolos na API Scryfall -> Symbol: " + symbols.get(i).getSymbol());
                downloadSvgAndConvertToPngService = new DownloadSvgAndConvertToPngService(
                        symbols.get(i).getImageUri(), symbolImgPath, downloadImageService);
                downloadSvgAndConvertToPngService.setSize(new Dimension(width, width));
                downloadSvgAndConvertToPngService.convert();
            }
        }
    }

}
