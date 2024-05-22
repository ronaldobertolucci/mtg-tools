package br.com.bertolucci.mtgtools.pngsvgtools.exception;

public class ImageDownloadException extends Exception {
    public ImageDownloadException(String message) {
        super(message);
    }
    public ImageDownloadException(Throwable cause) {
        super(cause);
    }
}
