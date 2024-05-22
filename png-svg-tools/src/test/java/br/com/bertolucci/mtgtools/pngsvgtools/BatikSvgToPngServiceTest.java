package br.com.bertolucci.mtgtools.pngsvgtools;

import br.com.bertolucci.mtgtools.pngsvgtools.converter.BatikSvgToPngService;
import br.com.bertolucci.mtgtools.pngsvgtools.converter.SvgToPngService;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageServiceImpl;
import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;
import br.com.bertolucci.mtgtools.pngsvgtools.util.CheckAndDeleteFileUtil;
import org.apache.batik.transcoder.TranscoderException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BatikSvgToPngServiceTest {

    private Path testDirectory;
    private String cacheFilename;
    private String resultFilename;
    private Path cachePath;
    private Path resultPath;
    private DownloadImageService downloadImageService;
    private SvgToPngService svgToPngService;

    @BeforeEach
    void setUp() throws IOException, ImageDownloadException {
        createTestDirectory();
        cachePath = Paths.get(cacheFilename);
        resultPath = Paths.get(resultFilename);

        svgToPngService = new BatikSvgToPngService(cacheFilename, resultFilename);

        // cria um cache.svg, a partir de zen.svg, para o teste
        downloadImageService = new DownloadImageServiceImpl();
        downloadImageService.downloadSvg(
                Paths.get("src/test/resources/zen.svg").toUri().toURL().toString(), cacheFilename);

    }

    @AfterEach
    void tearDown() throws IOException {
        CheckAndDeleteFileUtil.checkAndDelete(cachePath);
        CheckAndDeleteFileUtil.checkAndDelete(resultPath);
        CheckAndDeleteFileUtil.checkAndDelete(testDirectory);
    }

    @Test
    void testConvert() throws IOException, TranscoderException {
        assertTrue(Files.notExists(resultPath));

        svgToPngService.setPngSize(new Dimension(300, 300)).convert();
        assertTrue(Files.exists(resultPath));

        BufferedImage bimg = ImageIO.read(new File(resultFilename));
        assertEquals(300, bimg.getWidth());
        assertEquals(300, bimg.getHeight());
    }

    private void createTestDirectory() throws IOException {
        testDirectory = Files.createTempDirectory("testMtgTools");
        cacheFilename = testDirectory + "/cache.svg";
        resultFilename = testDirectory + "/result.png";
    }

}