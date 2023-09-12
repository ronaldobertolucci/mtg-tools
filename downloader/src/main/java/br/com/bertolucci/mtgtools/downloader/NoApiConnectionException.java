package br.com.bertolucci.mtgtools.downloader;

public class NoApiConnectionException extends Exception {
    public NoApiConnectionException(String message) {
        super(message);
    }
}
