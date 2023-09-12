package br.com.bertolucci.mtgtools.ui.symbol;


import br.com.bertolucci.mtgtools.deckbuilder.application.DeckBuilderService;
import br.com.bertolucci.mtgtools.deckbuilder.domain.symbol.Symbol;
import br.com.bertolucci.mtgtools.ui.AbstractListDialog;

import java.util.List;

public class SymbolListDialog extends AbstractListDialog<Symbol> {

    private List<Symbol> symbols;

    public SymbolListDialog(DeckBuilderService deckBuilderService) {
        super(new SymbolTableModel(deckBuilderService.getSymbols()), deckBuilderService);
        this.symbols = deckBuilderService.getSymbols();

        load();
        addButton.setVisible(false);
        init(contentPane, "Símbolos");
    }

    @Override
    protected void update(Symbol symbol) {
    }

    @Override
    protected void insert() {
    }

    @Override
    protected void remove(Symbol symbol) {
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
