package br.com.bertolucci.mtgtools.pngsvgtools;

import br.com.bertolucci.mtgtools.pngsvgtools.util.CheckAndDeleteFileUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DownloadImageServiceImplTest {

    private Path testDirectory;
    private String svgDownloadPath;
    private String pngDownloadPath;
    private Path svgDownload;
    private Path pngDownload;
    private DownloadImageService downloadImageService;


    @BeforeEach
    void setUp() throws IOException, ImageDownloadException {
        createTestDirectory();
        svgDownload = Paths.get(svgDownloadPath);
        pngDownload = Paths.get(pngDownloadPath);

        downloadImageService = new DownloadImageServiceImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        CheckAndDeleteFileUtil.checkAndDelete(svgDownload);
        CheckAndDeleteFileUtil.checkAndDelete(pngDownload);
        CheckAndDeleteFileUtil.checkAndDelete(testDirectory);
    }

    @Test
    void testDownloadSvg() throws MalformedURLException, ImageDownloadException {
        assertTrue(Files.notExists(svgDownload));

        downloadImageService.downloadSvg(
                Paths.get("src/test/resources/zen.svg").toUri().toURL().toString(),
                svgDownloadPath);

        assertTrue(Files.exists(svgDownload));
    }

    @Test
    void throwsExceptionWhenWrongUriDownloadSvg() {
        assertThrows(ImageDownloadException.class, () -> downloadImageService.downloadSvg(
                Paths.get("src/test/resources/test.svg").toUri().toURL().toString(),
                svgDownloadPath));
    }

    @Test
    void testDownloadPng() throws MalformedURLException, ImageDownloadException {
        assertTrue(Files.notExists(pngDownload));

        downloadImageService.downloadPng(
                Paths.get("src/test/resources/argivian.png").toUri().toURL().toString(),
                pngDownloadPath);

        assertTrue(Files.exists(pngDownload));
    }

    @Test
    void throwsExceptionWhenWrongUriDownloadPng() {
        assertThrows(ImageDownloadException.class, () -> downloadImageService.downloadPng(
                Paths.get("src/test/resources/test.png").toUri().toURL().toString(),
                svgDownloadPath));
    }

    private void createTestDirectory() throws IOException {
        testDirectory = Files.createTempDirectory("testMtgTools");
        pngDownloadPath = testDirectory + "/result.png";
        svgDownloadPath = testDirectory + "/result.svg";
    }

}