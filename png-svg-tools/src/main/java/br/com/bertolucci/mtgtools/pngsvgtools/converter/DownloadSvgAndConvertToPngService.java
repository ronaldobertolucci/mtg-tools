package br.com.bertolucci.mtgtools.pngsvgtools.converter;

import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;
import br.com.bertolucci.mtgtools.pngsvgtools.util.CheckAndDeleteFileUtil;
import org.apache.batik.transcoder.TranscoderException;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadSvgAndConvertToPngService {

    private final DownloadImageService downloadImageService;
    private SvgToPngService svgToPngService;
    private Path tempDir;
    private String cacheFilename;

    public DownloadSvgAndConvertToPngService(String uri, String filename, DownloadImageService downloadImageService)
            throws ImageDownloadException {

        this.downloadImageService = downloadImageService;
        initService(uri, filename);
    }

    public DownloadSvgAndConvertToPngService(String uri, Path filename, DownloadImageService downloadImageService)
            throws ImageDownloadException {
        this.downloadImageService = downloadImageService;
        initService(uri, filename.toString());
    }

    private void initService(String svgUri, String pngFilename) throws ImageDownloadException {
        createTempDir();
        svgToPngService = new BatikSvgToPngService(cacheFilename, pngFilename);
        downloadImageService.downloadSvg(svgUri, cacheFilename);
    }

    private void createTempDir() throws ImageDownloadException {
        try {
            tempDir = Files.createTempDirectory("imageCacheMtgTools");
        } catch (IOException e) {
            throw new ImageDownloadException("Impossível criar pasta temporária para o download do SVG");
        }

        cacheFilename = tempDir + "/cache.svg";
    }

    public DownloadSvgAndConvertToPngService setSize(Dimension dimension) {
        svgToPngService = svgToPngService.setPngSize(dimension);
        return this;
    }

    public DownloadSvgAndConvertToPngService setColor(String hexColor) throws ImageDownloadException {
        try {
            svgToPngService = svgToPngService.setColor(hexColor);
        } catch (IOException e) {
            throw new ImageDownloadException("Erro ao alterar a cor do arquivo SVG");
        }
        return this;
    }

    public void convert() throws ImageDownloadException {
        try {
            svgToPngService.convert();
        } catch (IOException | TranscoderException e) {
            throw new ImageDownloadException("Impossível converter o arquivo SVG para PNG");
        } finally {
            cleanUp(Paths.get(tempDir + "/cache.svg"));
            cleanUp(tempDir);
        }
    }

    private void cleanUp(Path path) throws ImageDownloadException {
        try {
            CheckAndDeleteFileUtil.checkAndDelete(path);
        } catch (IOException e) {
            throw new ImageDownloadException("Impossível limpar os arquivos em cache");
        }
    }

}
