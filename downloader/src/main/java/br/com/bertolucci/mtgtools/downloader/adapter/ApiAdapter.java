package br.com.bertolucci.mtgtools.downloader.adapter;

import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;

import java.util.List;

public interface ApiAdapter {

    <T> T getObject(String uri, Class<T> var) throws NoApiConnectionException;

    <T> List<T> getList(String uri, Class<T> var) throws NoApiConnectionException;

}
