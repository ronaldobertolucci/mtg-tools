package br.com.bertolucci.mtgtools.pngsvgtools.download;

import br.com.bertolucci.mtgtools.pngsvgtools.exception.ImageDownloadException;

public interface DownloadImageService {

    void downloadPng(String uri, String filename) throws ImageDownloadException;

    void downloadSvg(String uri, String filename) throws ImageDownloadException;

}
