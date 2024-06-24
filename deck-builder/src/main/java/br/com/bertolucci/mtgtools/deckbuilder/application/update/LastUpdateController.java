package br.com.bertolucci.mtgtools.deckbuilder.application.update;

import br.com.bertolucci.mtgtools.deckbuilder.domain.update.LastUpdate;
import br.com.bertolucci.mtgtools.deckbuilder.domain.update.LastUpdateRepositoryImpl;
import br.com.bertolucci.mtgtools.deckbuilder.infra.Repository;

import java.time.LocalDate;

public class LastUpdateController {

    private Repository<LastUpdate> updateRepository;

    public LastUpdateController(Repository<LastUpdate> updateRepository) {
        this.updateRepository = updateRepository;
    }

    public void save(LastUpdate update) {
        updateRepository.save(update);
    }

    public LocalDate findLastUpdate() {
        return ((LastUpdateRepositoryImpl) updateRepository).findLastUpdate();
    }

}
