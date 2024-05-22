package br.com.bertolucci.mtgtools.pngsvgtools;

import br.com.bertolucci.mtgtools.pngsvgtools.converter.GetPathNodeFromSvgService;
import br.com.bertolucci.mtgtools.pngsvgtools.converter.SvgColorChanger;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageService;
import br.com.bertolucci.mtgtools.pngsvgtools.download.DownloadImageServiceImpl;
import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;
import br.com.bertolucci.mtgtools.pngsvgtools.util.CheckAndDeleteFileUtil;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SvgColorChangerTest {

    private final String parser = XMLResourceDescriptor.getXMLParserClassName();
    private final SAXSVGDocumentFactory saxsvgDocumentFactory = new SAXSVGDocumentFactory(parser);
    private Document document;
    private Path testDirectory;
    private String cacheFilename;
    private Path cachePath;
    private DownloadImageService downloadImageService;
    private SvgColorChanger svgColorChanger;
    private GetPathNodeFromSvgService getPathNodeFromSvgService;

    @BeforeEach
    void setUp() throws IOException, ImageDownloadException {
        createTestDirectory();
        cachePath = Paths.get(cacheFilename);

        // cria um cache.svg, a partir de zen.svg, para o teste
        downloadImageService = new DownloadImageServiceImpl();
        downloadImageService.downloadSvg(
                Paths.get("src/test/resources/zen.svg").toUri().toURL().toString(), cacheFilename);

        svgColorChanger = new SvgColorChanger(cacheFilename);
        getPathNodeFromSvgService = new GetPathNodeFromSvgService();
    }

    @AfterEach
    void tearDown() throws IOException {
        CheckAndDeleteFileUtil.checkAndDelete(cachePath);
        CheckAndDeleteFileUtil.checkAndDelete(testDirectory);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"#PPP", "123"})
    void throwsExceptionWhenHexColorIsWrong(String color) {
        assertThrows(IOException.class, () -> svgColorChanger.change(color));
    }

    @Test
    void testChangeColor() {
        try {
            assertFalse(getNode().hasAttribute("style"));
            svgColorChanger.change("#FFF");
        } catch (IOException ignored) {
            fail();
        }

        try {
            assertEquals("fill:#FFF;", getNode().getAttributeNode("style").getValue());
        } catch (IOException e) {
            fail();
        }
    }

    private Element getNode() throws IOException {
        document = saxsvgDocumentFactory.createDocument(Paths.get(cacheFilename).toUri().toURL().toString());
        return (Element) getPathNodeFromSvgService.get(document);
    }

    private void createTestDirectory() throws IOException {
        testDirectory = Files.createTempDirectory("testMtgTools");
        cacheFilename = testDirectory + "/cache.svg";
    }

}