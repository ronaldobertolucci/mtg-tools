package br.com.bertolucci.mtgtools.deckbuilder.application.set;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;

public class FindSetService {

    private CollectionService collectionService;

    public FindSetService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    public Set find(Set set) {
        if (set == null) {
            return null;
        }

        Set searched = collectionService.findSetByCode(set.getCode());
        if (searched == null) {
            return set;
        }

        return searched;
    }

    public Set find(String setCode) {
        if (setCode == null) {
            return null;
        }

        return collectionService.findSetByCode(setCode);
    }

}
