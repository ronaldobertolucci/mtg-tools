package br.com.bertolucci.mtgtools.pngsvgtools.download;

import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadImageServiceImpl implements DownloadImageService {

    private File file;

    @Override
    public void downloadPng(String uri, String filename) throws ImageDownloadException {
        file = new File(filename);
        downloadBytes(file, uri);
    }

    @Override
    public void downloadSvg(String uri, String filename) throws ImageDownloadException {
        file = new File(filename);
        downloadBytes(file, uri);
    }

    private void downloadBytes(File file, String uri) throws ImageDownloadException {
        try (BufferedInputStream in = new BufferedInputStream(new URL(uri).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new ImageDownloadException("Impossível baixar o arquivo " + uri);
        }
    }
}
