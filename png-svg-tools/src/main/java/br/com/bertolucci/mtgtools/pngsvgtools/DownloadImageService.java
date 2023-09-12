package br.com.bertolucci.mtgtools.pngsvgtools;

import java.awt.*;

public interface DownloadImageService {

    void downloadPng(String uri, String filename) throws ImageDownloadException;

    void downloadSvg(String uri, String filename) throws ImageDownloadException;

}
