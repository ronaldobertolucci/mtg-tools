package br.com.bertolucci.mtgtools.downloader.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpConnectionUtil {

    private HttpClient client = HttpClient.newHttpClient();
    private HttpResponse<String> response;
    private HttpRequest request;

    public HttpResponse<String> getResponse(String uri) throws IOException, InterruptedException {
        request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

}
