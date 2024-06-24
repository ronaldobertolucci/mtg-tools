package br.com.bertolucci.mtgtools.deckbuilder.application.symbol;

import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.*;
import br.com.bertolucci.mtgtools.deckbuilder.infra.Repository;

import java.util.List;

public class SymbolController {

    private Repository<Symbol> symbolRepository;

    public SymbolController(Repository<Symbol> symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    public void saveAll(List<Symbol> symbols) {
        symbolRepository.saveAll(symbols);
    }

    public List<Symbol> findAll() {
        return symbolRepository.findAll();
    }
}
