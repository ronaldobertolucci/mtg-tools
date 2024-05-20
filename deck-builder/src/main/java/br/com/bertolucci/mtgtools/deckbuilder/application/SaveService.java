package br.com.bertolucci.mtgtools.deckbuilder.application;

import java.util.List;

public interface SaveService {
    <T> void save(T t);

    <T> void saveAll(List<T> t);
}
