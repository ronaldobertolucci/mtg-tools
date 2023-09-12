package br.com.bertolucci.mtgtools.pngsvgtools;

import org.apache.batik.transcoder.TranscoderException;

import java.awt.*;
import java.io.IOException;

public interface SvgToPngService {

    void convert() throws IOException, TranscoderException;

    BatikSvgToPngService setPngSize(Dimension dimension);

    SvgToPngService setColor(String hexColor) throws IOException;

}
