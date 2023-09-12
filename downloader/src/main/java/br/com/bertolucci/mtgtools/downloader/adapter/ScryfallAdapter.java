package br.com.bertolucci.mtgtools.downloader.adapter;

import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.downloader.util.HttpConnectionUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ScryfallAdapter implements ApiAdapter {

    private final Gson gson = new Gson();
    private final HttpConnectionUtil httpConnectionUtil;

    public ScryfallAdapter(HttpConnectionUtil httpConnectionUtil) {
        this.httpConnectionUtil = httpConnectionUtil;
    }

    @Override
    public <T> T getObject(String uri, Class<T> var) throws NoApiConnectionException {
        try {
            return gson.fromJson(this.httpConnectionUtil.getResponse(uri).body(), var);
        } catch (Exception e) {
            throw new NoApiConnectionException("Não foi possível obter a resposta para o download de " + var.getName());
        }
    }

    @Override
    public <T> List<T> getList(String uri, Class<T> var) throws NoApiConnectionException {
        ScryfallList<T> rawList = getRawList(uri, var);
        return extract(rawList, var);
    }

    public <T> ScryfallList<T> getRawList(String uri, Class<T> var) throws NoApiConnectionException {
        try {
            return gson.fromJson(httpConnectionUtil.getResponse(uri).body(),
                    TypeToken.getParameterized(ScryfallList.class, var).getType());
        } catch (Exception e) {
            throw new NoApiConnectionException("Não foi possível obter a resposta para o download de " + var.getName());
        }
    }

    private <T> List<T> extract(ScryfallList<T> rawList, Class<T> var) throws NoApiConnectionException {
        List<T> finalList = new ArrayList<>();

        while (true) {
            if (rawList.data() == null) {
                break;
            }

            finalList.addAll(rawList.data());

            if (!rawList.has_more()) {
                break;
            }

            rawList = getRawList(rawList.next_page(), var);
        }
        return finalList;
    }
}
