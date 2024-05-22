package br.com.bertolucci.mtgtools.pngsvgtools.converter;

import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

public class BatikSvgToPngService implements SvgToPngService {

    private SvgColorChanger svgColorChanger;
    private PNGTranscoder pngTranscoder;
    private String svgFilename;
    private String pngFilename;

    public BatikSvgToPngService(String svgFilename, String pngFilename) {
        this.svgColorChanger = new SvgColorChanger(svgFilename);
        this.pngTranscoder = new PNGTranscoder();
        this.svgFilename = svgFilename;
        this.pngFilename = pngFilename;
    }

    @Override
    public void convert() throws IOException, TranscoderException {
        try (OutputStream pngOutputStream = new FileOutputStream(pngFilename)) {
            TranscoderInput inputSvg = new TranscoderInput(Paths.get(svgFilename).toUri().toURL().toString());
            TranscoderOutput outputPng = new TranscoderOutput(pngOutputStream);
            pngTranscoder.transcode(inputSvg, outputPng);
            pngOutputStream.flush();
        }
    }

    @Override
    public BatikSvgToPngService setPngSize(Dimension dimension) {
        pngTranscoder.addTranscodingHint(SVGAbstractTranscoder.KEY_HEIGHT, (float) dimension.height);
        pngTranscoder.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, (float) dimension.width);
        return this;
    }

    @Override
    public BatikSvgToPngService setColor(String hexColor) throws IOException {
        svgColorChanger.change(hexColor);
        return this;
    }
}
