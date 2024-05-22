package br.com.bertolucci.mtgtools.pngsvgtools.converter;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.io.IOException;

public class GetPathNodeFromSvgService {

    private final XPathFactory xpathFactory = XPathFactory.newInstance();

    public Node get(Document document) throws IOException {
        XPath xpath = xpathFactory.newXPath();
        String xpathExpression = "descendant-or-self::*[@d]";

        XPathExpression expr;

        try {
            expr = xpath.compile(xpathExpression);
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            return nodes.item(0);
        } catch (XPathExpressionException e) {
            throw new IOException();
        }
    }

}
