package br.com.bertolucci.mtgtools.deckbuilder.application.set;

import br.com.bertolucci.mtgtools.deckbuilder.application.CollectionService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.Set;
import br.com.bertolucci.mtgtools.deckbuilder.domain.set.SetType;
import br.com.bertolucci.mtgtools.downloader.DownloadService;
import br.com.bertolucci.mtgtools.downloader.NoApiConnectionException;
import br.com.bertolucci.mtgtools.shared.set.SetDto;

import java.time.LocalDate;
import java.util.List;

public class InitSetsService {

    private DownloadService downloadService;
    private CollectionService collectionService;
    private List<SetDto> apiSets;

    public InitSetsService(DownloadService downloadService, CollectionService collectionService) {
        this.downloadService = downloadService;
        this.collectionService = collectionService;
    }

    // verifica se os sets da API estão no banco de dados local
    public void init() throws NoApiConnectionException {
        apiSets = downloadService.downloadSets();

        saveCoreSets();
        saveTokenSets();
    }

    private void saveCoreSets() {
        List<Set> localSets = collectionService.findAllSets();

        apiSets.forEach(setDto -> {
            Set set = new Set(setDto);
            if (isCore(set)) {
                save(localSets, set);
            }
        });
    }

    private boolean isCore(Set set) {
        return set.getType() == SetType.CORE || set.getType() == SetType.EXPANSION || set.getType() == SetType.DRAFT_INNOVATION;
    }

    private void saveTokenSets() {
        List<Set> localSets = collectionService.findAllSets();
        List<String> codes = localSets.stream().map(Set::getCode).toList();

        apiSets.forEach(setDto -> {
            if (setDto.type().equalsIgnoreCase("token") && codes.contains(setDto.parentSet())) {
                Set set = new Set(setDto);
                save(localSets, set);
            }
        });
    }

    private void save(List<Set> localSets, Set set) {
        if (!localSets.contains(set)) {
            collectionService.getSaveService().save(set);
            return;
        }

        update(set.getCode());
    }

    private void update(String setCode) {
        for (SetDto setDto: apiSets) {
            if (setDto.code().equalsIgnoreCase(setCode)) {
                Set set = collectionService.findSetByCode(setDto.code());

                if (set == null) {
                    return;
                }

                set.setTotalCards(setDto.totalCards());
                set.setImageUri(setDto.imageUri());
                set.setReleasedAt(LocalDate.parse(setDto.releasedAt()));
                set.setType(setDto.type());
                collectionService.getUpdateService().update(set);
            }
        }
    }

}
