package br.com.bertolucci.mtgtools.deckbuilder.infra;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    Optional<T> findById(Integer id);

    T save(T t);

    void saveAll(List<T> tList);

    void updateAll(List<T> tList);

    void removeAll(List<T> tList);

    void removeById(Integer id);

    List<T> findAll();

    T update(T t);

}
