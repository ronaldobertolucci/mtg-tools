package br.com.bertolucci.mtgtools.deckbuilder.application.set;

import br.com.bertolucci.mtgtools.deckbuilder.domain.set.*;
import br.com.bertolucci.mtgtools.deckbuilder.infra.Repository;

import java.util.List;

public class SetController {

    private Repository<Set> setRepository;

    public SetController(Repository<Set> setRepository) {
        this.setRepository = setRepository;
    }

    public void saveAll(List<Set> sets) {
        setRepository.saveAll(sets);
    }

    public List<Set> findAll() {
        return setRepository.findAll();
    }

    public List<Set> findTokenSets() {
        return ((SetRepositoryImpl) setRepository).findTokenSets();
    }

    public List<Set> findCoreSets() {
        return ((SetRepositoryImpl) setRepository).findCoreSets();
    }

}
