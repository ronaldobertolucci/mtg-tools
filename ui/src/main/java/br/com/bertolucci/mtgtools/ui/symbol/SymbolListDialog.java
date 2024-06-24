package br.com.bertolucci.mtgtools.ui.symbol;


import br.com.bertolucci.mtgtools.deckbuilder.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SymbolListDialog extends AbstractListDialog<Symbol> {

    private List<Symbol> symbols = new ArrayList<>();

    public SymbolListDialog(DeckBuilderService deckBuilderService) {
        super(new SymbolTableModel(deckBuilderService.getSymbols()));

        this.symbols.addAll(deckBuilderService.getSymbols());
        this.symbols.sort(Comparator.comparing(Symbol::getSymbol));

        load();
        init(contentPane, "Símbolos");
    }

    @Override
    protected void load() {
        table.setModel(new SymbolTableModel(symbols));
    }

    @Override
    protected void createUIComponents() {
        table = new SymbolTable();
    }
}
