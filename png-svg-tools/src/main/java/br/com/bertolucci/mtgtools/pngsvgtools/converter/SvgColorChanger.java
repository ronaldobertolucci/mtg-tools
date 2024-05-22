package br.com.bertolucci.mtgtools.pngsvgtools.converter;

import br.com.bertolucci.mtgtools.pngsvgtools.util.HexCodeValidator;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class SvgColorChanger {
    private final String parser = XMLResourceDescriptor.getXMLParserClassName();
    private final SAXSVGDocumentFactory saxsvgDocumentFactory = new SAXSVGDocumentFactory(parser);
    private final GetPathNodeFromSvgService getPathNodeFromSvgService = new GetPathNodeFromSvgService();
    private String filename;

    public SvgColorChanger(String filename) {
        this.filename = filename;
    }

    public void change(String hexColor) throws IOException {
        if (!HexCodeValidator.validate(hexColor)) {
            throw new IOException("Cor hexadecimal no formato incorreto");
        }

        Document document = saxsvgDocumentFactory.createDocument(Paths.get(filename).toUri().toURL().toString());
        Element node = (Element) getPathNodeFromSvgService.get(document);
        node.setAttribute("fill", hexColor);

        write(document, filename);
    }

    private void write(Document document, String uri) throws IOException {
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(document);

        try (Writer out = new OutputStreamWriter(new FileOutputStream(uri), StandardCharsets.UTF_8)) {
            svgGraphics2D.stream(document.getDocumentElement(), out, true, false);
        }
    }
}
